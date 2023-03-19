package scanner.history.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import scanner.history.dto.history.VisualDto;
import scanner.history.dto.report.ScanHistoryDetailDto;
import scanner.history.dto.report.ScanSummaryDto;
import scanner.history.entity.ScanHistory;
import scanner.history.entity.ScanHistoryDetail;
import scanner.history.repository.ScanHistoryDetailsRepository;
import scanner.history.repository.ScanHistoryRepository;
import scanner.history.dto.report.ReportResponse;

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

	public VisualDto.Response getVisualization(Long reportId) {

		ScanHistory history = scanHistoryRepository.findByHistorySeq(reportId);

		return new VisualDto.Response(history.getHistorySeq(), history.getCreatedAt(), history.getVisual());
	}
}
