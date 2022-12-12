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

    // 룰 on/off, 커스텀 수정
    @Modifying
    @Query(value = "UPDATE custom_rule SET modified_at = now(),  is_modified='y', rule_onoff = :state, custom_detail = :detail WHERE rule_seq = :id", nativeQuery = true)
    Integer updateRule(@Param("id") Long id, @Param("state") String state, @Param("detail") String detail);

    // 룰 on/off
    @Modifying
    @Query(value = "UPDATE custom_rule SET modified_at = now(), rule_onoff = :state WHERE rule_seq = :id", nativeQuery = true)
    Integer updateRuleOnOff(@Param("id") Long id, @Param("state") String state);

    // 룰 초기화
    @Modifying
    @Query(value = "UPDATE custom_rule SET modified_at = now(), is_modified='n', custom_detail = (SELECT custom_default FROM custom_rule WHERE rule_seq = :id) WHERE rule_seq = :id", nativeQuery = true)
    Integer resetRule(@Param("id") Long id);

    // on 또는 off된 룰만 검색
    List<CustomRule> findByRuleOnOff(String string);

    // 특정 id 룰만 검색
    List<CustomRule> findByIdIn(List<Long> id);

    CustomRule findById(Long id);

    CustomRule findByRuleId(String ruleId);
}
