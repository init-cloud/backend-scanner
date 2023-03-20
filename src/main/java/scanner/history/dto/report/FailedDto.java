package scanner.history.dto.report;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import scanner.checklist.entity.ComplianceEng;
import scanner.history.entity.ScanHistoryDetail;
import scanner.checklist.entity.Tag;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FailedDto {
	private String name;
	private Integer count;

	public static List<FailedDto> mapToDto(Map<String, Integer> map) {
		List<FailedDto> dto = new ArrayList<>();
		map.forEach((key, value) -> dto.add(new FailedDto(key, value)));

		return dto;
	}

	/* Resource */
	public static Map<String, Integer> toResourceMap(List<ScanHistoryDetail> details) {

		Map<String, Integer> resource = new LinkedHashMap<>();

		if (details.isEmpty())
			return resource;

		details.stream().forEach(detail -> {
			String key = detail.getResource();
			if (resource.containsKey(detail.getResource()))
				resource.put(key, resource.get(key) + 1);
			else
				resource.put(key, 1);
		});

		return resource;
	}

	/* ComplianceEng */
	public static Map<String, Integer> toComplianceMap(List<ScanHistoryDetail> details) {

		Map<String, Integer> compliance = new LinkedHashMap<>();

		if (details.isEmpty())
			return compliance;

		details.stream().forEach(detail -> {
			List<ComplianceEng> complianceEngs = detail.getRuleSeq().getComplianceEngs();

			for (int i = 0; i < complianceEngs.size(); i++) {
				String key = complianceEngs.get(i).getComplianceNumber();

				if (compliance.containsKey(key))
					compliance.put(key, compliance.get(key) + 1);
				else
					compliance.put(key, 1);
			}
		});

		return compliance;
	}

	/* Threat */
	public static Map<String, Integer> toThreatMap(List<ScanHistoryDetail> details) {

		Map<String, Integer> tag = new LinkedHashMap<>();

		if (details.isEmpty())
			return tag;

		details.stream().forEach(detail -> {
			List<Tag> tags = detail.getRuleSeq().getTags();

			for (int i = 0; i < tags.size(); i++) {
				String key = tags.get(i).getTagName();

				if (tag.containsKey(key))
					tag.put(key, tag.get(key) + 1);
				else
					tag.put(key, 1);
			}
		});

		return tag;
	}
}
