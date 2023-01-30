package scanner.dto.history;

import java.util.List;

import lombok.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HistoryResponse {
    List<HistoryDto> history;
}
