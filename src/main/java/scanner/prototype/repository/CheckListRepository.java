package scanner.prototype.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import scanner.prototype.model.CustomRule;

@Repository
public interface CheckListRepository extends JpaRepository<CustomRule, String> {
    
}
