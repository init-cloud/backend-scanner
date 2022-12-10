package scanner.prototype.dto.history.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FailedTargetDto {
    private String name;
    private Integer count;
}
