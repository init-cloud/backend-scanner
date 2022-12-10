package scanner.prototype.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import scanner.prototype.dto.history.report.PageOthersDto;
import scanner.prototype.dto.history.report.PageScanDto;
import scanner.prototype.dto.history.report.PageSummaryDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
    PageSummaryDto summary;
    List<PageScanDto> scan;
    List<PageOthersDto> details;
}
