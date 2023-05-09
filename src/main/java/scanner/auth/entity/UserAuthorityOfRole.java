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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scanner.common.entity.BaseEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAuthorityOfRole extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_AUTHORITY_OF_ROLE_ID")
	private Long id;

	@Column
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private UsersOfTeam team;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private UserAuthority userAuthority;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Feature feature;

	@OneToMany
	private List<UserRoleOfTeam> userRoleOfTeams = new ArrayList<>();
}
