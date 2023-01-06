package scanner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanResultDto {
    private String status;

    private String rule_id;
    private String description;

    private String target_resource;

    private String target_file;
    private String lines;

    private String level;
    private String detail;
}
