package scanner.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;

import scanner.user.enums.RoleType;
import scanner.user.enums.UserState;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserManagingDto extends UserRetrieveDto {

	Collection<? extends GrantedAuthority> authorities;

	public UserManagingDto(String username, UserState userState, RoleType role,
		Collection<? extends GrantedAuthority> authorities, LocalDateTime lastLogin) {
		super(username, userState, role, lastLogin);
		this.authorities = authorities;
	}
}
