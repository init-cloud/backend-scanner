package scanner.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import scanner.model.history.ScanHistory;

@Repository
public interface ScanHistoryRepository extends JpaRepository<ScanHistory, Long> {

	Optional<ScanHistory> findByHistorySeq(Long reportId);

	List<ScanHistory> findTop10ByOrderByHistorySeqDesc();
}
