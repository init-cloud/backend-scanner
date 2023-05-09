package scanner.auth.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scanner.common.entity.BaseEntity;
import scanner.user.entity.UsedRule;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UsersOfTeam extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USERS_OF_TEAM_ID")
	private Long id;

	@Column(columnDefinition = "varchar(1)") // default 'n'
	@NotNull
	private String isAdmin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Team team;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private User user;

	@OneToMany
	private List<UsedRule> usedRule = new ArrayList<>();
}
