package scanner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import scanner.dto.CommonResponse;
import scanner.dto.history.VisualDto;
import scanner.service.history.ScanHistoryService;

@RestController
@RequestMapping("/api/v1/visual")
@RequiredArgsConstructor
public class VisualizerController {

	private final ScanHistoryService scanHistoryService;

	@GetMapping("/{history}")
	public ResponseEntity<CommonResponse<VisualDto.Response>> scanVisualization(
		@PathVariable("history") Long historySeq
	) {
		VisualDto.Response dto = scanHistoryService.getVisualization(historySeq);

		return ResponseEntity.ok()
			.body(new CommonResponse<>(dto));
	}
}
