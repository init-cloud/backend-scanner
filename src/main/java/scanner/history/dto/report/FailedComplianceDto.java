package scanner.history.dto.report;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scanner.checklist.entity.ComplianceEng;
import scanner.checklist.entity.ComplianceKor;
import scanner.history.entity.ScanHistoryDetail;

/*
	@Written by @Floodnut, v0.3.1-beta
	@Todo
	DB tables need to be redesigned.
	After the design, the classes are integrated into 'ComplianceDto'
	We're going to refactoring language-specific methods.
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FailedComplianceDto {
	private String compliance;
	private List<FailedDto> failedDto;

	public FailedComplianceDto(String compliance, List<FailedDto> failedDto) {
		this.compliance = compliance;
		this.failedDto = failedDto;
	}

	public static List<FailedComplianceDto> toFailedComplianceList(Map<String, Map<String, Integer>> map) {
		List<FailedComplianceDto> dtoList = new ArrayList<>();

		map.forEach((complianceName, complianceDetail) -> {
			List<FailedDto> details = complianceDetail.entrySet()
				.stream()
				.map(entry -> new FailedDto(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
			dtoList.add(new FailedComplianceDto(complianceName, details));
		});

		return dtoList;
	}

	/* ComplianceEng */
	public static Map<String, Map<String, Integer>> toEnglishDto(List<ScanHistoryDetail> details) {
		Map<String, Map<String, Integer>> total = new LinkedHashMap<>();
		details.stream().forEach(detail -> {
			for (ComplianceEng cmp : detail.getRuleSeq().getComplianceEngs()) {
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

	public static Map<String, Map<String, Integer>> toKoreanDto(List<ScanHistoryDetail> details) {
		Map<String, Map<String, Integer>> total = new LinkedHashMap<>();
		details.stream().forEach(detail -> {
			for (ComplianceKor cmp : detail.getRuleSeq().getComplianceKors()) {
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

