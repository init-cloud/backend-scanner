package scanner.prototype.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import scanner.prototype.model.ScanHistory;

@Repository
public interface ScanHistoryRepository extends JpaRepository<ScanHistory, String>  {
    
}
