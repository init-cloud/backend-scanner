package scanner.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import scanner.dto.history.report.ScanHistoryDetailDto;
import scanner.dto.history.report.ScanSummaryDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportResponse {
    ScanSummaryDto summary;
    List<ScanHistoryDetailDto> details;

    public ReportResponse(ScanSummaryDto summary, List<ScanHistoryDetailDto> details) {
        this.summary = summary;
        this.details = details;
    }
}
