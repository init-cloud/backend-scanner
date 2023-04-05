package scanner.history.dto.report;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.*;
import scanner.common.enums.Language;
import scanner.history.entity.ScanHistory;
import scanner.history.entity.ScanHistoryDetail;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScanSummaryDto {

	@Builder
	public static class Content {
		private Long historySeq;
		private String date;
		private String csp;
		private String scanTarget;
		private String scanTargetHash;
		private Integer totalScanned;
		private Integer passed;
		private Integer skipped;
		private Integer failed;
		private Integer high;
		private Integer medium;
		private Integer low;
		private Integer unknown;
		private Double score;
		private List<FailedDto> failedResource;
		private List<FailedComplianceDto> failedCompliance;
		private List<FailedDto> failedSecurityThreat;
	}

	public static ScanSummaryDto.Content mapDtoByLanguage(ScanHistory entity, Language lang) {

		List<ScanHistoryDetail> details = entity.getDetails();

		Map<String, Integer> resource = FailedDto.toResourceMap(details);
		Map<String, Integer> threat = FailedDto.toThreatMap(details);
		Map<String, Map<String, Integer>> compliance = new LinkedHashMap<>();



		if (!details.isEmpty()) {
			if (lang == Language.KOREAN)
				compliance = FailedComplianceDto.toKoreanDto(details);
			else
				compliance = FailedComplianceDto.toEnglishDto(details);
		}

		return toDtoWithFailedInfo(entity, resource, compliance, threat);
	}

	private static ScanSummaryDto.Content toDtoWithFailedInfo(ScanHistory entity, Map<String, Integer> resource,
		Map<String, Map<String, Integer>> compliance, Map<String, Integer> threat) {
		return Content.builder()
			.historySeq(entity.getHistorySeq())
			.date(entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.csp(entity.getCsp())
			.scanTarget(entity.getFileName())
			.scanTargetHash(entity.getFileHash())
			.totalScanned(entity.getPassed() + entity.getFailed() + entity.getSkipped())
			.passed(entity.getPassed())
			.skipped(entity.getSkipped())
			.failed(entity.getFailed())
			.high(entity.getHigh())
			.medium(entity.getMedium())
			.low(entity.getLow())
			.unknown(entity.getUnknown())
			.score(entity.getScore())
			.failedResource(FailedDto.mapToDto(resource))
			.failedCompliance(FailedComplianceDto.toFailedComplianceList(compliance))
			.failedSecurityThreat(FailedDto.mapToDto(threat))
			.build();
	}
}
