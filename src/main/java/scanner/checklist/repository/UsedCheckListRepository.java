package scanner.checklist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import scanner.checklist.entity.UsedRule;

@Repository
public interface UsedCheckListRepository extends JpaRepository<UsedRule, Long> {
	// 커스텀 수정
	@Modifying
	@Query(value = "UPDATE used_rule SET modified_at = now(),  is_modified='y', custom_detail = :detail WHERE rule_name = :rule_name", nativeQuery = true)
	Integer updateRule(@Param("rule_name") String ruleName, @Param("detail") String detail);

	// 룰 on/off
	@Modifying
	@Query(value = "UPDATE used_rule SET modified_at = now(), rule_onoff = :state WHERE rule_name = :rule_name", nativeQuery = true)
	Integer updateRuleOnOff(@Param("rule_name") String ruleName, @Param("state") String state);

	// 룰 초기화
	@Modifying
	@Query(value = "UPDATE used_rule SET modified_at = now(), is_modified='n', custom_detail = (SELECT custom_default FROM custom_rule WHERE default_rule_name = :rule_name) WHERE rule_name = :rule_name", nativeQuery = true)
	Integer resetRule(@Param("rule_name") String ruleName);

	// on 또는 off 된 룰만 검색
	List<UsedRule> findByIsOn(String string);

	// 특정 id 룰만 검색
	List<UsedRule> findByRuleNameIn(List<String> ruleId);

	// 문자열이 포함된 id 룰 검색
	List<UsedRule> findByRuleNameContains(String ruleId);

	Optional<UsedRule> findByRuleName(String ruleId);

	Optional<UsedRule> findById(Long id);
}
