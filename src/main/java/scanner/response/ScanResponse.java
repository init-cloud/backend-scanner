package scanner.response;

import java.util.List;

import lombok.Getter;

import scanner.dto.CheckResultDto;
import scanner.dto.ParseResultDto;
import scanner.dto.ScanResultDto;

@Getter
public class ScanResponse {
    private final CheckResultDto check;
    private final List<ScanResultDto> result;
    private final ParseResultDto parse;

    public ScanResponse(CheckResultDto check, List<ScanResultDto> result, ParseResultDto parse) {
        this.check = check;
        this.result = result;
        this.parse = parse;
    }
}