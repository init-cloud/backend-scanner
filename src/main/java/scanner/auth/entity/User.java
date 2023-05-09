package scanner.auth.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import scanner.common.entity.BaseEntity;
import scanner.user.dto.UserAuthDto;
import scanner.user.dto.UserDetailsDto;
import scanner.user.enums.OAuthProvider;
import scanner.user.enums.RoleType;
import scanner.user.enums.UserAuthority;
import scanner.user.enums.UserState;

import javax.validation.constraints.Size;

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

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	@Column(name = "USERNAME")
	@Size(max = 32)
	private String username;

	@Column(name = "PASSWORD")
	@Setter
	private String password;

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

	@Column(name = "EMAIL")
	@Size(max = 128)
	private String email;

	@Column(name = "contact")
	@Size(max = 16)
	private String contact;

	@OneToMany
	private List<UsersOfTeam> usersOfTeams = new ArrayList<>();

	@Builder(builderClassName = "modifyUserBuilder", builderMethodName = "modifyUserInfoBuilder")
	public User(User user, LocalDateTime lastLogin, String password, String authorities, RoleType roleType,
		UserState userState, String email, String contact) {
		super(user.getCreatedAt(), user.getModifiedAt());
		this.id = user.getId();
		this.lastLogin = lastLogin;
		this.isOAuthed = user.getIsOAuthed();
		this.oAuthProvider = user.getOAuthProvider();
		this.lastLogin = user.getLastLogin();
		this.username = user.getUsername();
		this.password = password;
		this.authorities = authorities;
		this.roleType = roleType;
		this.userState = userState;
		this.email = email;
		this.contact = contact;
	}

	public static User toEntityByModifying(User user, LocalDateTime lastLogin, String password, String authorities,
		RoleType roleType, UserState userState, String email, String contact) {
		return User.modifyUserInfoBuilder()
			.user(user)
			.lastLogin(lastLogin)
			.password(password)
			.roleType(roleType)
			.authorities(authorities)
			.userState(userState)
			.email(email)
			.contact(contact)
			.build();
	}

	public User(String username, String password, Character isOAuthed, OAuthProvider oAuthProvider, RoleType roleType,
		String authorities, UserState userState, String email, String contact) {
		this.isOAuthed = isOAuthed;
		this.oAuthProvider = oAuthProvider;
		this.username = username;
		this.password = password;
		this.roleType = roleType;
		this.authorities = authorities;
		this.userState = userState;
		this.email = email;
		this.contact = contact;
	}

	public void modifyUserLoginDateTime() {
		this.lastLogin = LocalDateTime.now();
	}

	public void modifyUserContactsProfile(UserDetailsDto.Profile dto) {
		this.email = dto.getEmail();
		this.contact = dto.getContact();
	}

	public static User addUser(UserAuthDto.Signup dto, String password) {
		return new User(dto.getUsername(), password, 'n', OAuthProvider.NONE, RoleType.GUEST,
			UserAuthority.GUEST.toString(), UserState.ACTIVATE, dto.getEmail(), dto.getContact());
	}

	public static String getAuthorities(Collection<? extends GrantedAuthority> authorities) {
		StringBuilder sb = new StringBuilder();

		for (GrantedAuthority authority : authorities)
			sb.append(authority.getAuthority());

		return sb.toString();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorityList = new ArrayList<>();

		for (String authority : this.authorities.split(","))
			authorityList.add(new SimpleGrantedAuthority(authority));

		return authorityList;
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
}
