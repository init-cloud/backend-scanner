package scanner.checklist;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import scanner.dto.report.FailedComplianceDto;
import scanner.model.enums.Language;
import scanner.model.enums.Provider;
import scanner.model.history.ScanHistory;
import scanner.model.history.ScanHistoryDetail;
import scanner.model.rule.ComplianceEng;
import scanner.model.rule.ComplianceKor;
import scanner.model.rule.CustomRule;
import scanner.repository.ScanHistoryRepository;

@SpringBootTest
class ComplianceTest {

	@Mock
	private ScanHistoryRepository scanHistoryRepository;

	@Mock
	private ScanHistory scanHistory;

	@Test
	@DisplayName("Compliance Mapping Test")
	void mapComplianceTest() {

		// given
		CustomRule rule = new CustomRule("CKV_NCP_1", "CKV_NCP_1", Provider.NCP, null, null, null);
		ComplianceKor complianceKor = new ComplianceKor(rule, "ISMS-P", "0.0.0", "카테고리", "아티클", "컴플라이언스 설명", "");
		ComplianceEng complianceEng = new ComplianceEng(rule, "ISMS-P", "0.0.0", "Category", "Article", "description",
			"");
		ScanHistoryDetail detail = new ScanHistoryDetail(rule, scanHistory, "resource", "resourceName", "scanResult",
			"file.tf", "line", "code");

		List<ComplianceKor> kors = new ArrayList<>();
		kors.add(complianceKor);
		List<ComplianceEng> engs = new ArrayList<>();
		engs.add(complianceEng);
		List<ScanHistoryDetail> details = new ArrayList<>();
		details.add(detail);

		rule.setComplianceKors(kors);
		rule.setComplianceEngs(engs);
		rule.setHistoryDetails(details);

		// when
		doReturn(details).when(scanHistory).getDetails();

		//then
		List<ScanHistoryDetail> detailsTest = scanHistory.getDetails();

		Map<String, Map<String, Integer>> complianceMapEng = FailedComplianceDto.toComplianceMap(detailsTest);
		Map<String, Map<String, Integer>> complianceMapKor = FailedComplianceDto.toComplianceKorMap(detailsTest);

		Assertions.assertTrue(complianceMapEng.get("ISMS-P").containsKey("0.0.0"));
		Assertions.assertTrue(complianceMapKor.get("ISMS-P").containsKey("0.0.0"));
	}
}
