package scanner.user.entity;

import lombok.Getter;
import scanner.auth.entity.User;
import scanner.common.entity.BaseEntity;
import scanner.checklist.entity.CustomRule;

import javax.persistence.*;

@Getter
@Entity
public class UsedRule extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USED_RULE_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RULE_SEQ")
	private CustomRule customRule;
}
