package scanner.prototype.response;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScanResponse<T> {
    private final CheckResponse check;
    private final List<ResultResponse> result;

    public static <T> ScanResponse<T> toDto(CheckResponse check, List<ResultResponse> results) {
        return new ScanResponse(check, results);
    }
}
