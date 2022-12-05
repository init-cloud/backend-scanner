package scanner.prototype.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import scanner.prototype.response.ApiResponse;
import scanner.prototype.service.ScanService;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ScanHistoryController {

    private final ScanService scanService;

    @GetMapping("/history")
    public ApiResponse<?> retrieveHistory() 
    {
        return null;
    }
}
