package scanner.prototype.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import scanner.prototype.response.ApiResponse;
import scanner.prototype.response.HistoryResponse;
import scanner.prototype.response.ReportResponse;
import scanner.prototype.service.ScanHistoryService;
import scanner.prototype.service.ScanService;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ScanHistoryController {

    private final ScanHistoryService scanHistoryService;

    /**
     * 
     */
    @GetMapping("/history")
    public ApiResponse<?> retrieveHistory() {
        List<HistoryResponse> history = scanHistoryService.retrieveHistoryList();
        return ApiResponse.success("data", history);
    }

    /**
     * 
     * @param reportId
     * @return
     */
    @GetMapping("/report/{reportId}")
    public ApiResponse<?> retrieveReport(
        @PathVariable String reportId
    ){
        ReportResponse report = scanHistoryService.retrieveReport(reportId);
        return ApiResponse.success("data", report);
    }
}
