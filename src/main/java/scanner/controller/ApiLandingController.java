package scanner.controller;

import java.util.HashMap;

import io.swagger.annotations.ApiOperation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import scanner.dto.CommonResponse;

@ApiOperation("API Landing check")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ApiLandingController {

	@ApiOperation(value = "API List",
		notes = "API List",
		response = ResponseEntity.class)
	@GetMapping
	public ResponseEntity<CommonResponse<HashMap<String, String>>> retrieveAPI() {
		HashMap<String, String> api = new HashMap<>();
		api.put("checklist", "/api/v1/checklist");
		api.put("scan", "/api/v1/file/{provider}");
		api.put("history", "/api/v1/history");
		api.put("report", "/api/v1/report/{reportId}");

		return ResponseEntity.ok()
			.body(new CommonResponse<>(api));
	}
}
