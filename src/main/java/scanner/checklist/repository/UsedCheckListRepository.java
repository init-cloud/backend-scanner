package scanner.checklist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import scanner.checklist.entity.UsedRule;
import scanner.user.entity.User;

@Repository
public interface UsedCheckListRepository extends JpaRepository<UsedRule, Long> {
	// 커스텀 수정
	@Modifying
	@Query(value = "UPDATE used_rule SET modified_at = now(), is_modified='y', custom_detail = :detail WHERE user_id = :user_id and rule_name = :rule_name", nativeQuery = true)
	Integer updateRule(@Param("user_id") Long userId, @Param("rule_name") String ruleName,
		@Param("detail") String detail);

	// 룰 on/off
	@Modifying
	@Query(value = "UPDATE used_rule SET modified_at = now(), rule_onoff = :state WHERE user_id = :user_id and rule_name = :rule_name", nativeQuery = true)
	Integer updateRuleOnOff(@Param("user_id") Long userId, @Param("rule_name") String ruleName, @Param("state") String state);

	// 룰 초기화
	@Modifying
	@Query(value = "UPDATE used_rule SET modified_at = now(), is_modified='n', custom_detail = (SELECT custom_default FROM custom_rule WHERE default_rule_name = :rule_name) WHERE user_id = :user_id and rule_name = :rule_name", nativeQuery = true)
	Integer resetRule(@Param("user_id") Long userId, @Param("rule_name") String ruleName);

	// on 또는 off 된 룰만 검색
	List<UsedRule> findByUserAndIsOn(User user, String string);

	// 특정 id 룰만 검색
	List<UsedRule> findByUserAndRuleNameIn(User user, List<String> ruleId);

	// 문자열이 포함된 id 룰 검색
	List<UsedRule> findByUserAndRuleNameContains(User user, String ruleId);

	Optional<UsedRule> findByUserAndRuleName(User user, String ruleId);

	Optional<UsedRule> findByUserAndId(User user, Long id);
}
