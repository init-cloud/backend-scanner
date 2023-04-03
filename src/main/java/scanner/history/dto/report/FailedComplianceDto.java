package scanner.history.dto.report;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scanner.checklist.entity.ComplianceEng;
import scanner.checklist.entity.ComplianceKor;
import scanner.history.entity.ScanHistoryDetail;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FailedComplianceDto {
	private String compliance;
	private List<FailedDto> failedDto;

	public FailedComplianceDto(String compliance, List<FailedDto> failedDto) {
		this.compliance = compliance;
		this.failedDto = failedDto;
	}

	public static List<FailedComplianceDto> mapToComplianceDto(Map<String, Map<String, Integer>> map) {
		List<FailedComplianceDto> dto = new ArrayList<>();

		map.forEach((compliance, data) -> {
			List<FailedDto> details = new ArrayList<>();
			data.forEach((key, value) -> details.add(new FailedDto(key, value)));
			dto.add(new FailedComplianceDto(compliance, details));
		});

		return dto;
	}

	/* ComplianceEng */
	public static Map<String, Map<String, Integer>> toComplianceMap(List<ScanHistoryDetail> details) {

		Map<String, Map<String, Integer>> total = new LinkedHashMap<>();

		if (details.isEmpty())
			return total;

		details.stream().forEach(detail -> {
			List<ComplianceEng> compliances = detail.getRuleSeq().getComplianceEngs();
			for (ComplianceEng cmp : compliances) {
				Map<String, Integer> compliance;
				if (total.containsKey(cmp.getComplianceName()))
					compliance = total.get(cmp.getComplianceName());
				else
					compliance = new LinkedHashMap<>();

				String key = cmp.getComplianceNumber();
				if (compliance.containsKey(key))
					compliance.put(key, compliance.get(key) + 1);
				else
					compliance.put(key, 1);

				total.put(cmp.getComplianceName(), compliance);
			}
		});

		return total;
	}

	public static Map<String, Map<String, Integer>> toComplianceKorMap(List<ScanHistoryDetail> details) {

		Map<String, Map<String, Integer>> total = new LinkedHashMap<>();

		if (details.isEmpty())
			return total;

		details.stream().forEach(detail -> {
			List<ComplianceKor> compliances = detail.getRuleSeq().getComplianceKors();
			for (ComplianceKor cmp : compliances) {
				Map<String, Integer> compliance;
				if (total.containsKey(cmp.getComplianceName()))
					compliance = total.get(cmp.getComplianceName());
				else
					compliance = new LinkedHashMap<>();

				String key = cmp.getComplianceNumber();
				if (compliance.containsKey(key))
					compliance.put(key, compliance.get(key) + 1);
				else
					compliance.put(key, 1);

				total.put(cmp.getComplianceName(), compliance);
			}
		});

		return total;
	}
}

