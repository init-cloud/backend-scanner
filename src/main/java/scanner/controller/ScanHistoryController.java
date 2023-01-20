package scanner.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import scanner.dto.history.HistoryDto;
import scanner.response.CommonResponse;
import scanner.response.ReportResponse;
import scanner.service.ScanHistoryService;
import scanner.model.ScanHistory;


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
    public ResponseEntity<?> retrieveHistory() {
        try{
            List<ScanHistory> history = scanHistoryService.retrieveHistoryList();

            List<HistoryDto> dtos = history.stream()
                                            .map(HistoryDto::new)
                                            .collect(Collectors.toList());

            return ResponseEntity.ok()
                    .body(new CommonResponse(dtos));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(null);
        }
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
