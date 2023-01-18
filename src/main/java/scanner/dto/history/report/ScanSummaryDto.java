package scanner.dto.history.report;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import scanner.model.ScanHistory;
import scanner.model.ScanHistoryDetail;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanSummaryDto {
    private Long historySeq;
    private String date;
    private String CSP;
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

    public static ScanSummaryDto toDto(ScanHistory entity){
        
        List<ScanHistoryDetail> details = entity.getDetails();

        LinkedHashMap<String, Integer> resource = FailedResourceDto.toMapDto(details);
        LinkedHashMap<String, Integer> compliance = FailedComplianceDto.toMapDto(details);
        LinkedHashMap<String, Integer> threat = FailedThreatDto.toMapDto(details);

        return ScanSummaryDto.builder()
                        .historySeq(entity.getHistorySeq())
                        .date(entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .CSP(entity.getCsp())
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
                        .failedResource(FailedDto.mapToDtp(resource))
                        .failedCompliance(FailedDto.mapToDtp(compliance))
                        .failedSecurityThreat(FailedDto.mapToDtp(threat))
                        .build();
    }
}
