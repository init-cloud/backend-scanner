package scanner.prototype.dto.history.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageScanDto {
    private String ruleID;
    private String policy;
    private String severity;
    private String scanResult;
    private String solution;
    private String controlName;
    private String article;
}
