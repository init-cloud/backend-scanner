package scanner.history.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import scanner.history.entity.ScanHistory;

@Repository
public interface ScanHistoryRepository extends JpaRepository<ScanHistory, Long> {

	ScanHistory findByHistorySeq(Long reportId);

	List<ScanHistory> findTop10ByOrderByHistorySeqDesc();
}
