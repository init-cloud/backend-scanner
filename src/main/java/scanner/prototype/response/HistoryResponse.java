package scanner.prototype.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import scanner.prototype.dto.history.HistoryDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryResponse {
    List<HistoryDto> history;
}
