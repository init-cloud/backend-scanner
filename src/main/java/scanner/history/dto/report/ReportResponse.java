package scanner.history.dto.report;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportResponse {
	ScanSummaryDto.Content summary;
	List<ScanHistoryDetailDto> details = new ArrayList<>();

	public ReportResponse(ScanSummaryDto.Content summaryContent, List<ScanHistoryDetailDto> details) {
		this.summary = summaryContent;
		this.details = details;
	}
}
