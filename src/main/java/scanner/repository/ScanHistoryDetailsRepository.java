package scanner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import scanner.model.ScanHistoryDetail;

@Repository
public interface ScanHistoryDetailsRepository extends JpaRepository<ScanHistoryDetail, Long>  {
    List<ScanHistoryDetail> findByHistorySeq(Long historySeq);
}
