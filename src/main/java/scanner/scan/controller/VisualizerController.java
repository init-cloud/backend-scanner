package scanner.scan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import scanner.common.dto.ResponseDto;
import scanner.history.dto.history.VisualDto;
import scanner.history.service.ScanHistoryService;

@ApiOperation("History for scan visualization.")
@RestController
@RequestMapping("/api/v1/visual")
@RequiredArgsConstructor
public class VisualizerController {

	private final ScanHistoryService scanHistoryService;

	@ApiOperation(value = "Scan Visualization", notes = "Get Scan Visualization from History.", response = ResponseDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "history", paramType = "path", value = "History ID", required = true, dataTypeClass = Long.class)})
	@GetMapping("/{history}")
	public ResponseDto<VisualDto.Response> scanVisualization(@PathVariable("history") Long historySeq) {
		VisualDto.Response dto = scanHistoryService.getVisualization(historySeq);

		return new ResponseDto<>(dto);
	}
}
