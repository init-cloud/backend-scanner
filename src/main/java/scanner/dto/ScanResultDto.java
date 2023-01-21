package scanner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanResultDto {
    private String status;

    private String ruleId;
    private String description;

    private String targetResource;

    private String targetFile;
    private String lines;

    private String level;
    private String detail;
}
