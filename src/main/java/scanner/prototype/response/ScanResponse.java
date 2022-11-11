package scanner.prototype.response;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScanResponse<T> {
    private final CheckResponse check;
    private final List<ResultResponse> result;
    private final ParseResponse parse;

    public static <T> ScanResponse<T> toDto(
        CheckResponse check, 
        List<ResultResponse> results,
        ParseResponse parse
    ) {
        return new ScanResponse(check, results, parse);
    }
}