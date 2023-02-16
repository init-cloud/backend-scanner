package scanner.service.history;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import scanner.dto.report.ScanHistoryDetailDto;
import scanner.dto.report.ScanSummaryDto;
import scanner.model.history.ScanHistory;
import scanner.model.history.ScanHistoryDetail;
import scanner.repository.ScanHistoryDetailsRepository;
import scanner.repository.ScanHistoryRepository;
import scanner.dto.report.ReportResponse;

@Service
@RequiredArgsConstructor
public class ScanHistoryService {

	private final ScanHistoryRepository scanHistoryRepository;
	private final ScanHistoryDetailsRepository scanHistoryDetailsRepository;

	public List<ScanHistory> getHistoryList() {
		return scanHistoryRepository.findTop10ByOrderByHistorySeqDesc();
	}

	@Transactional
	public ReportResponse getReportDetails(Long reportId) {

		ScanHistory history = scanHistoryRepository.findByHistorySeq(reportId);

		List<ScanHistoryDetail> details = scanHistoryDetailsRepository.findByHistorySeq(reportId);

		ScanSummaryDto summaryDto = ScanSummaryDto.toDto(history);

		List<ScanHistoryDetailDto> detailsDto = details.stream()
			.map(ScanHistoryDetailDto::toDto)
			.collect(Collectors.toList());

		return new ReportResponse(summaryDto, detailsDto);
	}
}
