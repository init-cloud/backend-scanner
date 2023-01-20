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
public class ScanResponse {
    private final CheckResultDto check;
    private final List<ScanResultDto> result;
    private final ParseResultDto parse;
}