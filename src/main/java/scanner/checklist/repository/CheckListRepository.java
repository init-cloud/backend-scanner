package scanner.checklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import scanner.checklist.entity.CustomRule;

@Repository
public interface CheckListRepository extends JpaRepository<CustomRule, Long> {

}
