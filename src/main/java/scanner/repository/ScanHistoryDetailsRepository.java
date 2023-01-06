package scanner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import scanner.model.ScanHistoryDetail;

@Repository
public interface ScanHistoryDetailsRepository extends JpaRepository<ScanHistoryDetail, String>  {

    @Query(value = "SELECT * FROM scan_history_detail WHERE history_seq= :id", nativeQuery = true)
    List<ScanHistoryDetail> findByHistorySeq(@Param("id") Long id); 
}
