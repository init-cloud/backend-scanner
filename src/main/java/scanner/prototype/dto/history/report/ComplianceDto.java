package scanner.prototype.dto.history.report;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import scanner.prototype.model.CustomRule;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplianceDto {
    private String name;

    public static ComplianceDto toDto(final CustomRule entity) {

        //List<Compliance> compl = entity.getCompliance();

        return ComplianceDto.builder()
                            .build();
    }
}
