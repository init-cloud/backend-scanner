package scanner.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import scanner.dto.history.report.ScanHistoryDetailDto;
import scanner.dto.history.report.ScanSummaryDto;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
    ScanSummaryDto summary;
    List<ScanHistoryDetailDto> details;
}
