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
    private Integer failed;
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
                        .scanTarget(entity.getFileName())
                        .scanTargetHash(entity.getFileHash())
                        .totalScanned(entity.getPassed() + entity.getFailed())
                        .passed(entity.getPassed())
                        .failed(entity.getFailed())
                        .score(entity.getScore())
                        .failedResource(resource)
                        .failedCompliance(compliance)
                        .failedSecurityThreat(threat)
                        .build();
    }
}
