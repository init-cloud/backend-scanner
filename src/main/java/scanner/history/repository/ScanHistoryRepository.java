package scanner.history.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import scanner.history.entity.ScanHistory;

import java.util.Optional;

@Repository
public interface ScanHistoryRepository extends JpaRepository<ScanHistory, Long> {

	Optional<ScanHistory> findById(Long reportId);

	List<ScanHistory> findTop10ByOrderByIdDesc();
}
