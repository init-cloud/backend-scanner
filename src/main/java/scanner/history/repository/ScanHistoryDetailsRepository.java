package scanner.history.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import scanner.history.entity.ScanHistoryDetail;

@Repository
public interface ScanHistoryDetailsRepository extends JpaRepository<ScanHistoryDetail, Long> {

	@Query(value = "SELECT * FROM scan_history_detail WHERE history_id= :history_id", nativeQuery = true)
	List<ScanHistoryDetail> findByHistory(@Param("history_id") Long historyId);
}
