package scanner.prototype.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScanResponse<T> {
    private final CheckResponse check;
    private final List<ResultResponse> result;

    public static <T> ScanResponse<T> toDto(String name, T body) {
        List<ResultResponse> result = new ArrayList<ResultResponse>();

        /* 테스트 용 */
        ResultResponse a = new ResultResponse(name, name, name, name, name, name, name, name, name);
        result.add(a);

        return new ScanResponse(
            new CheckResponse(0, 0, 0), result);
    }
}
