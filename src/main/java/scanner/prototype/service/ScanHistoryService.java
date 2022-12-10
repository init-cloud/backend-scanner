package scanner.prototype.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.prototype.repository.ScanHistoryRepository;
import scanner.prototype.response.HistoryResponse;
import scanner.prototype.response.ReportResponse;

@Service
@RequiredArgsConstructor
public class ScanHistoryService {
    private ScanHistoryRepository scanHistoryRepository;

    public List<HistoryResponse> retrieveHistoryList(){
        return null;
    }

    public ReportResponse retrieveReport(String reportId){
        return null;
    }
}
