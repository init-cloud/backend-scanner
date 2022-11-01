package scanner.prototype.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckResponse {
    private int passed;
    private int failed;
    private int skipped;
}
