package scanner.prototype.dto.history.report;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageOthersDto {
    private String ruleID;
    private String result;
    private String Severity;
    private String description;
    private String type;
    private String fileName;
    private String line;
    private String resource;
    private String resourceName;
    private String compliance;
    private String article;
    private String problamaticCode;
    List<ComplianceDto> unfulfilledCompliance;

    public static PageOthersDto toDto() {
        return PageOthersDto.builder()
                            .build();
    }
}
