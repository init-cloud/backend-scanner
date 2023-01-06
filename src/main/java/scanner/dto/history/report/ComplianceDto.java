package scanner.dto.history.report;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import scanner.model.Compliance;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplianceDto {
    private String compliance;
    private String complianceNumber;
    private String description;

    public static ComplianceDto toDto(final Compliance entity) {

        return ComplianceDto.builder()
                            .compliance(entity.getComplianceName())
                            .complianceNumber(entity.getComplianceNumber())
                            .description(entity.getDescription())
                            .build();
    }
}
