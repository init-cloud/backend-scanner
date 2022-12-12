package scanner.prototype.dto.history.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import scanner.prototype.model.ScanHistoryDetail;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FailedThreatDto {
    private String name;
    private Integer count;

    public static FailedThreatDto toDto(ScanHistoryDetail detail){

        return FailedThreatDto.builder()
                                .name("-")
                                .count(0)
                                .build();
    }
}
