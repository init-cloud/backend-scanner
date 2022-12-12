package scanner.prototype.dto.history.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import scanner.prototype.model.Compliance;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FailedComplianceDto {
    private String name;
    private Integer count;

    public static FailedComplianceDto toDto(Compliance compliance){

        return FailedComplianceDto.builder()
                                .name("-")
                                .count(0)
                                .build();
        
    }
}
