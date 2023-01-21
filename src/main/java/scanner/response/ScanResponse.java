package scanner.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

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