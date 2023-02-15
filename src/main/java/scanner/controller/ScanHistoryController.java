package scanner.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scanner.dto.history.HistoryDto;
import scanner.model.ScanHistory;
import scanner.dto.CommonResponse;
import scanner.dto.report.ReportResponse;
import scanner.service.ScanHistoryService;

import java.util.List;
import java.util.stream.Collectors;


@ApiOperation("ScanHistory API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ScanHistoryController {

    private final ScanHistoryService scanHistoryService;

    @ApiOperation(value = "Retrieve Scan History",
            notes = "Retrieve scan histories for reports.",
            response = ResponseEntity.class)
    @GetMapping("/history")
    public ResponseEntity<CommonResponse<List<HistoryDto>>> retrieveHistory() {
        List<ScanHistory> history = scanHistoryService.retrieveHistoryList();

        List<HistoryDto> dtos = history.stream()
                .map(HistoryDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(new CommonResponse<>(dtos));
    }

    @ApiOperation(value = "Retrieve Scan Report",
            notes = "Retrieve report from Scan histories.",
            response = ResponseEntity.class)
    @GetMapping("/report/{reportId}")
    public ResponseEntity<CommonResponse<ReportResponse>> retrieveReport(
            @PathVariable Long reportId
    ){
        ReportResponse dtos = scanHistoryService.retrieveReport(reportId);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(dtos));
    }
}