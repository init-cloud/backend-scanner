package scanner.history.dto.report;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
