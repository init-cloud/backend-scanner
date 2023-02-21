package scanner.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import scanner.dto.history.HistoryDto;
import scanner.model.history.ScanHistory;
import scanner.dto.CommonResponse;
import scanner.dto.report.ReportResponse;
import scanner.service.history.ScanHistoryService;

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
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),})
	@GetMapping("/history")
	public CommonResponse<List<HistoryDto>> historyList() {
		List<ScanHistory> history = scanHistoryService.getHistoryList();

		List<HistoryDto> dtos = history.stream().map(HistoryDto::new).collect(Collectors.toList());

		return new CommonResponse<>(dtos);
	}

	@ApiOperation(value = "Retrieve Scan Report", notes = "Retrieve report from Scan histories.", response = CommonResponse.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "reportId", paramType = "path", value = "History(Report) ID", required = true, dataTypeClass = Long.class)})
	@GetMapping("/report/{reportId}")
	public CommonResponse<ReportResponse> reportDetails(@PathVariable Long reportId) {
		ReportResponse dtos = scanHistoryService.getReportDetails(reportId);

		return new CommonResponse<>(dtos);
	}
}