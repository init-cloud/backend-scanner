package scanner.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import scanner.dto.CheckResultDto;
import scanner.dto.ParseResultDto;
import scanner.dto.ScanResultDto;

@Getter
@AllArgsConstructor
public class ScanResponse<T> {
    private final CheckResultDto check;
    private final List<ScanResultDto> result;
    private final ParseResultDto parse;

//    public static <T> ScanResponse<T> toDto(
//        CheckResultDto check,
//        List<ScanResultDto> results,
//        ParseResultDto parse
//    ) {
//        return new ScanResponse<T>(check, results, parse);
//    }
}