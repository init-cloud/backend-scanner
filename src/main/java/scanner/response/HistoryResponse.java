package scanner.response;

import java.util.List;

import lombok.*;
import scanner.dto.history.HistoryDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HistoryResponse {
    List<HistoryDto> history;

    public HistoryResponse(List<HistoryDto> history) {
        this.history = history;
    }
}
