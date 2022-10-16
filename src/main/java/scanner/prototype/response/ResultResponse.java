package scanner.prototype.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ResultResponse {
    private String status;
    private String provider;
    private String rule_id;
    private String description;
    private String level;
    private String target_file;
    private String target_resource;
    private String lines;
    private String detail;
}
