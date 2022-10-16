package scanner.prototype.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CheckResponse {
    private int passed;
    private int failed;
    private int skipped;
}
