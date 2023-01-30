package scanner.dto.scan;

import java.util.List;

import lombok.Getter;

import scanner.dto.checklist.CheckResultDto;

@Getter
public class ScanResponse {
    private final CheckResultDto check;
    private final List<ScanResultDto> result;
    private final Object parse;

    public ScanResponse(CheckResultDto check, List<ScanResultDto> result, Object parse) {
        this.check = check;
        this.result = result;
        this.parse = parse;
    }
}