package scanner.history.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import scanner.common.dto.CommonResponse;

import scanner.common.enums.Language;
import scanner.history.dto.history.HistoryDto;
import scanner.history.dto.report.ReportResponse;
import scanner.history.entity.ScanHistory;
import scanner.history.service.ScanHistoryService;

import java.util.List;
import java.util.stream.Collectors;

@ApiOperation("ScanHistory API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ScanHistoryController {

	private final ScanHistoryService scanHistoryService;

	@ApiOperation(value = "Retrieve Scan History", notes = "Retrieve scan histories for reports.", response = CommonResponse.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class)})
	@GetMapping("/history")
	public CommonResponse<List<HistoryDto>> historyList() {
		List<ScanHistory> history = scanHistoryService.getHistoryList();

		List<HistoryDto> dtos = history.stream().map(HistoryDto::new).collect(Collectors.toList());

		return new CommonResponse<>(dtos);
	}

	@ApiOperation(value = "Retrieve Scan Report", notes = "Retrieve report from Scan histories.", response = CommonResponse.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "reportId", paramType = "path", value = "History(Report) ID", required = true, dataTypeClass = Long.class),
		@ApiImplicitParam(name = "lang", paramType = "query", value = "eng, kor", required = false, dataTypeClass = String.class)})
	@GetMapping("/report/{reportId}")
	public CommonResponse<ReportResponse> reportDetails(@PathVariable Long reportId,
		@Nullable @RequestParam String lang) {
		ReportResponse dtos = scanHistoryService.getReportDetails(reportId, Language.of(lang));

		return new CommonResponse<>(dtos);
	}
}