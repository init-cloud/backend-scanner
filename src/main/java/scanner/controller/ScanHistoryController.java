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
import scanner.response.CommonResponse;
import scanner.response.ReportResponse;
import scanner.service.ScanHistoryService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ScanHistoryController {

    private final ScanHistoryService scanHistoryService;

    /**
     *
     * @return
     */
    @GetMapping("/history")
    public ResponseEntity<CommonResponse<List<HistoryDto>>> retrieveHistory() {
        List<ScanHistory> history = scanHistoryService.retrieveHistoryList();

        List<HistoryDto> dtos = history.stream()
                                        .map(HistoryDto::new)
                                        .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(new CommonResponse(dtos));
    }

    /**
     *
     * @param reportId
     * @return
     */
    @GetMapping("/report/{reportId}")
    public ResponseEntity<?> retrieveReport(
        @PathVariable Long reportId
    ){
        ReportResponse dtos = scanHistoryService.retrieveReport(reportId);

        return ResponseEntity.ok()
                .body(new CommonResponse(dtos));
    }
}
