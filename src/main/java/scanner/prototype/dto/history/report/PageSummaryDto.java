package scanner.prototype.dto.history.report;

import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import scanner.prototype.model.ScanHistory;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageSummaryDto {
    private Long historySeq;
    private String date;
    private String CSP;
    private String scanTarget;
    private String scanTargetHash;
    private Integer totalScanned;
    private Integer passed;
    private Integer failed;
    private Double score;
    private List<FailedTargetDto> failedResource;
    private List<FailedTargetDto> failedISMSP;
    private List<FailedTargetDto> failedSecurityThreat;

    public static PageSummaryDto toDto(ScanHistory entity) {
        return PageSummaryDto.builder()
                        .historySeq(entity.getId())
                        .date(entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .scanTarget(entity.getFileName())
                        .scanTargetHash(entity.getFileHash())
                        .totalScanned(entity.getPassed() + entity.getFailed())
                        .passed(entity.getPassed())
                        .failed(entity.getFailed())
                        .score(entity.getScore())
                        .failedResource(null)
                        .failedISMSP(null)
                        .failedSecurityThreat(null)
                        .build();
    }
}
