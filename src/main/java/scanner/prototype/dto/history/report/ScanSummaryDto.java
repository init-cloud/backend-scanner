package scanner.prototype.dto.history.report;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import scanner.prototype.model.ScanHistory;
import scanner.prototype.model.ScanHistoryDetail;

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
    private Map<String, Integer> failedResource;
    private Map<String, Integer> failedCompliance;
    private Map<String, Integer> failedSecurityThreat;

    public static ScanSummaryDto toDto(ScanHistory entity){
        
        List<ScanHistoryDetail> details = entity.getDetails();

        Map<String, Integer> resource = FailedResourceDto.toDto(details);

        Map<String, Integer> compliance = null;
        
        Map<String, Integer> threat = null;

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
                        .failedResource(resource)
                        .failedCompliance(compliance)
                        .failedSecurityThreat(threat)
                        .build();
    }
}
