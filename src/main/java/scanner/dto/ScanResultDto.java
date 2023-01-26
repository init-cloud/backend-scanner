package scanner.dto;

import lombok.*;


@Getter
@Setter
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

    @Setter
    private String detail;
}
