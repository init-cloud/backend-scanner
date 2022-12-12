package scanner.prototype.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import scanner.prototype.dto.history.HistoryDto;
import scanner.prototype.response.ApiResponse;
import scanner.prototype.response.ReportResponse;
import scanner.prototype.service.ScanHistoryService;
import scanner.prototype.model.ScanHistory;


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
        List<ScanHistory> history = scanHistoryService.retrieveHistoryList();

        List<HistoryDto> scan = history.stream()
                                        .map(HistoryDto::new)
                                        .collect(Collectors.toList());

        return ApiResponse.success("data", scan);
    }

    /**
     * 
     * @param reportId
     * @return
     */
    @GetMapping("/report/{reportId}")
    public ApiResponse<?> retrieveReport(
        @PathVariable Long reportId
    ){
        ReportResponse report = scanHistoryService.retrieveReport(reportId);

        return ApiResponse.success("data", report);
    }
}
