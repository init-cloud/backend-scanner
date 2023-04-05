package scanner.history.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import scanner.common.enums.Language;
import scanner.common.exception.ApiException;
import scanner.history.dto.history.VisualDto;
import scanner.history.dto.report.ScanHistoryDetailDto;
import scanner.history.dto.report.ScanSummaryDto;
import scanner.history.entity.ScanHistory;
import scanner.history.entity.ScanHistoryDetail;
import scanner.history.repository.ScanHistoryDetailsRepository;
import scanner.history.repository.ScanHistoryRepository;
import scanner.history.dto.report.ReportResponse;

import scanner.common.enums.ResponseCode;

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

		ScanSummaryDto.Content summaryDto = ScanSummaryDto.mapDtoByLanguage(history, lang);

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
