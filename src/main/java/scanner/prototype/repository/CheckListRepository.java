package scanner.prototype.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import scanner.prototype.model.CustomRule;

@Repository
public interface CheckListRepository extends JpaRepository<CustomRule, String> {

    @Modifying
    @Query(value = "UPDATE custom_rule set rule_onoff = :state where rule_seq = :id", nativeQuery = true)
    List<CustomRule> updateRuleOnOff(@Param("id") Long id, @Param("state") String state);

    List<CustomRule> findByRuleOnOff(String string);
}
