package scanner.prototype.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import scanner.prototype.dto.history.report.ScanHistoryDetailDto;
import scanner.prototype.dto.history.report.ScanSummaryDto;
import scanner.prototype.model.ScanHistory;
import scanner.prototype.model.ScanHistoryDetail;
import scanner.prototype.repository.ScanHistoryDetailsRepository;
import scanner.prototype.repository.ScanHistoryRepository;
import scanner.prototype.response.ReportResponse;

@Service
@RequiredArgsConstructor
public class ScanHistoryService {

    private final ScanHistoryRepository scanHistoryRepository;
    private final ScanHistoryDetailsRepository scanHistoryDetailsRepository;

    /**
     * 
     * @return
     */
    public List<ScanHistory> retrieveHistoryList(){
        return scanHistoryRepository.findTop10ByOrderByHistorySeqDesc();
    }

    /**
     * 
     * @param reportId
     * @return
     */
    @Transactional
    public ReportResponse retrieveReport(Long reportId){

        ScanHistory history = scanHistoryRepository.findByHistorySeq(reportId);

        System.out.println(history.getHistorySeq());

        List<ScanHistoryDetail> details = scanHistoryDetailsRepository.findByHistorySeq(reportId);

        ScanSummaryDto summaryDto = ScanSummaryDto.toDto(history);

        List<ScanHistoryDetailDto> detailsDto = details.stream()
                                        .map(ScanHistoryDetailDto::toDto)
                                        .collect(Collectors.toList());

        return new ReportResponse(summaryDto, detailsDto);
    }
}
