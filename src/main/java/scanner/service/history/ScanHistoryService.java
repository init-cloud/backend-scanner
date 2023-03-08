package scanner.service.history;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import scanner.common.enums.ResponseCode;
import scanner.dto.history.VisualDto;
import scanner.dto.report.ScanHistoryDetailDto;
import scanner.dto.report.ScanSummaryDto;
import scanner.exception.ApiException;
import scanner.model.enums.Language;
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
	public ReportResponse getReportDetails(Long reportId, Language lang) {

		ScanHistory history = scanHistoryRepository.findByHistorySeq(reportId)
			.orElseThrow(() -> new ApiException(ResponseCode.NO_SCAN_RESULT));

		ScanSummaryDto summaryDto = ScanSummaryDto.toLangDto(history, lang);

		List<ScanHistoryDetail> details = scanHistoryDetailsRepository.findByHistorySeq(reportId);
		List<ScanHistoryDetailDto> detailsDto;
		if (lang == Language.KOREAN)
			detailsDto = details.stream().map(ScanHistoryDetailDto::toKor).collect(Collectors.toList());
		else
			detailsDto = details.stream().map(ScanHistoryDetailDto::toEng).collect(Collectors.toList());

		return new ReportResponse(summaryDto, detailsDto);
	}

	public VisualDto.Response getVisualization(Long reportId) {

		ScanHistory history = scanHistoryRepository.findByHistorySeq(reportId)
			.orElseThrow(() -> new ApiException(ResponseCode.NO_SCAN_RESULT));

		return new VisualDto.Response(history.getHistorySeq(), history.getCreatedAt(), history.getVisual());
	}
}
