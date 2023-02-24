package scanner.dto.report;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scanner.model.history.ScanHistoryDetail;
import scanner.model.rule.Compliance;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FailedComplianceDto {
	private String compliance;
	private List<FailedDto> failedDto;

	public FailedComplianceDto(String compliance, List<FailedDto> failedDto) {
		this.compliance = compliance;
		this.failedDto = failedDto;
	}

	public static List<FailedComplianceDto> mapToCompliacnDto(Map<String, Map<String, Integer>> map) {
		List<FailedComplianceDto> dto = new ArrayList<>();

		map.forEach((compliance, data) -> {
			List<FailedDto> details = new ArrayList<>();
			data.forEach((key, value) -> details.add(new FailedDto(key, value)));
			dto.add(new FailedComplianceDto(compliance, details));
		});

		return dto;
	}

	/* Compliance */
	public static Map<String, Map<String, Integer>> toComplianceMap(List<ScanHistoryDetail> details) {

		Map<String, Map<String, Integer>> total = new LinkedHashMap<>();

		if (details.isEmpty())
			return total;

		details.stream().forEach(detail -> {
			List<Compliance> compliances = detail.getRuleSeq().getCompliance();
			for (Compliance cmp : compliances) {
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
