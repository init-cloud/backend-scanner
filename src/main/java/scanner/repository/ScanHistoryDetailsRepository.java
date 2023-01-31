package scanner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import scanner.model.ScanHistoryDetail;

@Repository
public interface ScanHistoryDetailsRepository extends JpaRepository<ScanHistoryDetail, Long>  {

    @Query(value = "SELECT * FROM scan_history_detail WHERE history_seq= :history_seq", nativeQuery = true)
    List<ScanHistoryDetail> findByHistorySeq(@Param("history_seq") Long history_seq);
}
