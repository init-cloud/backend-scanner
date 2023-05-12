package scanner.user.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scanner.checklist.entity.UsedRule;
import scanner.common.entity.BaseEntity;
import scanner.oauth.dto.OAuthDto;
import scanner.user.dto.UserAuthDto;
import scanner.user.enums.OAuthProvider;
import scanner.user.enums.RoleType;
import scanner.user.enums.UserAuthority;
import scanner.user.enums.UserState;

@Getter
@Entity
@Table(name = "USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long id;

	@Column(name = "IS_OAUTHED")
	private Character isOAuthed;

	@Column(name = "OAUTH_PROVIDER")
	@Enumerated(EnumType.STRING)
	private OAuthProvider oAuthProvider;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
	private UserOAuthToken oAuthToken;

	@Column(name = "LAST_LOGIN")
	private LocalDateTime lastLogin;

	@Column(name = "NICKNAME", length = 255)
	private String nickname;

	@Column(name = "USERNAME", length = 255)
	private String username;

	@Column(name = "PASSWORD")
	@Setter
	private String password;

	@Column(name = "PROFILE_IMAGE_URL")
	private String profileImageUrl;

	@Column(name = "AUTHORITIES")
	@Setter
	private String authorities;

	@Column(name = "ROLE_TYPE")
	@Enumerated(EnumType.STRING)
	@Setter
	private RoleType roleType;

	@Column(name = "USER_STATE")
	@Enumerated(EnumType.STRING)
	private UserState userState;

	@Setter
	@Column(name = "EMAIL")
	@Size(max = 128)
	private String email;

	@Setter
	@Column(name = "contact")
	@Size(max = 16)
	private String contact;

	@OneToMany(mappedBy = "user")
	private List<UsedRule> usedRules = new ArrayList<>();

	/**
	 * Constructor for Modifying Methods.
	 */
	@Builder(builderClassName = "userModifyBuilder", builderMethodName = "userModifyBuilder")
	public User(User user, LocalDateTime lastLogin, String password, String authorities, RoleType roleType,
		UserState userState, String email, String contact) {
		super(user.getCreatedAt(), user.getModifiedAt());
		this.id = user.getId();
		this.lastLogin = lastLogin;
		this.isOAuthed = user.getIsOAuthed();
		this.oAuthProvider = user.getOAuthProvider();
		this.lastLogin = user.getLastLogin();
		this.nickname = user.getNickname();
		this.username = user.getUsername();
		this.password = password;
		this.authorities = authorities;
		this.roleType = roleType;
		this.userState = userState;
		this.email = email;
		this.contact = contact;
	}

	/**
	 * Constructor for Individual social User
	 */
	public User(String username, String nickname, OAuthProvider oAuthProvider, String authorities, RoleType roleType,
		UserState userState) {
		super(LocalDateTime.now(), LocalDateTime.now());
		this.lastLogin = LocalDateTime.now();
		this.isOAuthed = 'y';
		this.oAuthProvider = oAuthProvider;
		this.nickname = nickname;
		this.username = username;
		this.password = "";
		this.authorities = authorities;
		this.roleType = roleType;
		this.userState = userState;
		this.email = "";
		this.contact = "";
	}

	/**
	 * Constructor for User who has team.
	 */
	public User(String username, String nickname, String password, Character isOAuthed, OAuthProvider oAuthProvider,
		RoleType roleType, String authorities, UserState userState, String email, String contact) {
		this.isOAuthed = isOAuthed;
		this.oAuthProvider = oAuthProvider;
		this.nickname = nickname;
		this.username = username;
		this.password = password;
		this.roleType = roleType;
		this.authorities = authorities;
		this.userState = userState;
		this.email = email;
		this.contact = contact;
	}

	/**
	 * Add Social User with Github, has no team.
	 * @return User
	 */
	public static User addIndividualSocialUser(OAuthDto.GithubUserDetail detail) {
		return new User(detail.getLogin(), detail.getName(), OAuthProvider.GITHUB, UserAuthority.ADMIN.toString(),
			RoleType.ADMIN, UserState.ACTIVATE);
	}

	/**
	 * Add User, has no team.
	 * @return User
	 */
	public static User addIndividualUser(UserAuthDto.Signup dto, String password) {
		return new User(dto.getUsername(), dto.getNickname(), password, 'n', OAuthProvider.NONE, RoleType.ADMIN,
			UserAuthority.ADMIN.toString(), UserState.ACTIVATE, dto.getEmail(), dto.getContact());
	}

	/**
	 * Add User, has team.
	 * @return User
	 */
	public static User addUser(UserAuthDto.Signup dto, String password) {
		return new User(dto.getUsername(), dto.getNickname(), password, 'n', OAuthProvider.NONE, RoleType.GUEST,
			UserAuthority.GUEST.toString(), UserState.ACTIVATE, dto.getEmail(), dto.getContact());
	}

	/**
	 * @return String authorities
	 */
	public static String getAuthorities(Collection<? extends GrantedAuthority> authorities) {
		StringBuilder sb = new StringBuilder();

		for (GrantedAuthority authority : authorities)
			sb.append(authority.getAuthority());

		return sb.toString();
	}

	/**
	 * @return Collection authoritiees
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorityList = new ArrayList<>();

		for (String authority : this.authorities.split(","))
			authorityList.add(new SimpleGrantedAuthority(authority));

		return authorityList;
	}

	/**
	 * Update User Login time to now.
	 */
	public void modifyUserLastLoginNow() {
		this.lastLogin = LocalDateTime.now();
	}

	/**
	 * Modify password self.
	 */
	public void modifyUserPassword(String password) {
		this.password = password;
	}

	public String getAuthoritiesToString() {
		return this.authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return this.userState.equals(UserState.ACTIVATE);
	}

	/* For Test code */

	public User(Long id) {
		this.id = id;
	}
}
