package scanner.dto.report;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import lombok.*;
import scanner.model.history.ScanHistory;
import scanner.model.history.ScanHistoryDetail;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScanSummaryDto {
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
	private List<FailedDto> failedCompliance;
	private List<FailedDto> failedSecurityThreat;

	public static ScanSummaryDto toDto(ScanHistory entity) {

		List<ScanHistoryDetail> details = entity.getDetails();

		Map<String, Integer> resource = FailedDto.toResourceMap(details);
		Map<String, Integer> compliance = FailedDto.toComplianceMap(details);
		Map<String, Integer> threat = FailedDto.toThreatMap(details);

		return ScanSummaryDto.builder()
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
			.failedCompliance(FailedDto.mapToDto(compliance))
			.failedSecurityThreat(FailedDto.mapToDto(threat))
			.build();
	}
}
