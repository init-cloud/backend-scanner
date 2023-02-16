package scanner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import scanner.dto.CommonResponse;

@RestController
@RequestMapping("/api/v1/visual")
public class VisualizerController {

	@GetMapping("/{history}")
	public ResponseEntity<CommonResponse<?>> scanVisualization(
		@PathVariable("history") Long historySeq
	) {

		return ResponseEntity.ok()
			.body(new CommonResponse<>(null));
	}
}
