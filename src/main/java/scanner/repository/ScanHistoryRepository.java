package scanner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import scanner.model.ScanHistory;

@Repository
public interface ScanHistoryRepository extends JpaRepository<ScanHistory, String>  {

    ScanHistory findByHistorySeq(Long reportId); 

    List<ScanHistory> findTop10ByOrderByHistorySeqDesc();
}
