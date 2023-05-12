package scanner.history.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import scanner.common.enums.Language;
import scanner.common.exception.ApiAuthException;
import scanner.common.exception.ApiException;
import scanner.history.dto.history.VisualDto;
import scanner.history.dto.report.ScanHistoryDetailDto;
import scanner.history.dto.report.ScanSummaryDto;
import scanner.history.entity.ScanHistory;
import scanner.history.entity.ScanHistoryDetail;
import scanner.history.repository.ScanHistoryRepository;
import scanner.history.dto.report.ReportResponse;

import scanner.common.enums.ResponseCode;
import scanner.security.provider.JwtTokenProvider;
import scanner.user.entity.User;
import scanner.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ScanHistoryService {

	private final ScanHistoryRepository scanHistoryRepository;
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;

	public VisualDto.Response getVisualization(Long reportId) {
		User currentUser = getCurrentUser();

		ScanHistory history = scanHistoryRepository.findByUserAndId(currentUser, reportId)
			.orElseThrow(() -> new ApiException(ResponseCode.NO_SCAN_RESULT));

		return new VisualDto.Response(history.getId(), history.getCreatedAt(), history.getVisual());
	}

	public List<ScanHistory> getHistoryList() {
		User currentUser = getCurrentUser();

		return scanHistoryRepository.findTop10ByUserOrderByIdDesc(currentUser);
	}

	@Transactional
	public ReportResponse getReportDetails(Long reportId, Language lang) {
		ScanHistory scanHistory = getScanHistory(reportId);
		ScanSummaryDto.Content summaryDto = createSummaryDto(scanHistory, lang);
		List<ScanHistoryDetailDto> detailsDto = createDetailsDto(scanHistory.getDetails(), lang);
		return new ReportResponse(summaryDto, detailsDto);
	}

	private ScanHistory getScanHistory(Long reportId) {
		User currentUser = getCurrentUser();

		return scanHistoryRepository.findByUserAndId(currentUser, reportId)
			.orElseThrow(() -> new ApiException(ResponseCode.NO_SCAN_RESULT));
	}

	private ScanSummaryDto.Content createSummaryDto(ScanHistory scanHistory, Language lang) {
		return ScanSummaryDto.mapDtoByLanguage(scanHistory, lang);
	}

	private List<ScanHistoryDetailDto> createDetailsDto(List<ScanHistoryDetail> details, Language lang) {
		List<ScanHistoryDetailDto> detailsDto;
		if (lang == Language.KOREAN) {
			detailsDto = details.stream().map(ScanHistoryDetailDto::toKor).collect(Collectors.toList());
		} else {
			detailsDto = details.stream().map(ScanHistoryDetailDto::toEng).collect(Collectors.toList());
		}
		return detailsDto;
	}

	private User getCurrentUser() {
		String username = jwtTokenProvider.getUsername();
		return userRepository.findByUsername(username)
			.orElseThrow(() -> new ApiAuthException(ResponseCode.INVALID_USER));
	}
}
