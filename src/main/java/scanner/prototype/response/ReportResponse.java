package scanner.prototype.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import scanner.prototype.dto.history.report.ScanHistoryDetailDto;
import scanner.prototype.dto.history.report.ScanSummaryDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
    ScanSummaryDto summary;
    List<ScanHistoryDetailDto> details;
}
