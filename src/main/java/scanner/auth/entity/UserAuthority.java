package scanner.auth.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scanner.common.entity.BaseEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAuthority extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_AUTHORITY_ID")
	private Long id;

	@Column
	private String authorityName;

	@Column
	private String authorityType;

	@Column
	private Integer authorityPriority;

	@OneToMany
	private List<UserAuthorityOfRole> userAuthorityOfRoles = new ArrayList<>();
}
