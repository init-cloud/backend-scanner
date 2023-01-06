package scanner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckResultDto {
    private int passed;
    private int failed;
    private int skipped;
}
