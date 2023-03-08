package scanner.model.user;

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
import scanner.dto.user.UserSignupDto;
import scanner.model.BaseEntity;
import scanner.model.enums.OAuthProvider;
import scanner.model.enums.RoleType;
import scanner.model.enums.UserAuthority;
import scanner.model.enums.UserState;

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
	@Setter
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

	@Builder
	public User(LocalDateTime lastLogin, String username, String password, Character isOAuthed,
		OAuthProvider oAuthProvider, RoleType roleType, String authorities, UserState userState, String email,
		String contact, List<UsedRule> usedRules) {

		this.lastLogin = lastLogin;
		this.isOAuthed = isOAuthed;
		this.oAuthProvider = oAuthProvider;
		this.username = username;
		this.password = password;
		this.roleType = roleType;
		this.authorities = authorities;
		this.userState = userState;
		this.email = email;
		this.contact = contact;
		this.usedRules = usedRules;
	}

	public static User toEntity(UserSignupDto dto, String password) {
		return User.builder()
			.username(dto.getUsername())
			.password(password)
			.isOAuthed('n')
			.oAuthProvider(OAuthProvider.NONE)
			.roleType(RoleType.GUEST)
			.authorities(UserAuthority.GUEST.toString())
			.userState(UserState.ACTIVATE)
			.email(dto.getEmail())
			.contact(dto.getContact())
			.build();
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
