package scanner.user.dto;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scanner.user.entity.User;
import scanner.user.enums.RoleType;
import scanner.user.enums.UserState;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailsDto {

	@Getter
	public static class Retrieve extends UserBaseDto {
		private UserState userState;
		private RoleType role;

		public Retrieve(String username, UserState userState, RoleType role, LocalDateTime lastLogin) {
			super(username, lastLogin);
			this.userState = userState;
			this.role = role;
		}

		public Retrieve(User user) {
			super(user.getUsername(), user.getLastLogin());
			this.userState = user.getUserState();
			this.role = user.getRoleType();
		}
	}

	@Getter
	public static class Profile extends UserBaseDto {
		private String email;
		private String contact;
		private RoleType role;

		public Profile(String username, String email, String contact, RoleType role, LocalDateTime lastLogin) {
			super(username, lastLogin);
			this.role = role;
			this.email = email;
			this.contact = contact;
		}
	}

	@Getter
	public static class Managing extends UserBaseDto {
		private UserState userState;
		private RoleType role;
		private Collection<? extends GrantedAuthority> authorities;

		public Managing(String username, LocalDateTime lastLogin, UserState userState, RoleType role,
			Collection<? extends GrantedAuthority> authorities) {
			super(username, lastLogin);
			this.userState = userState;
			this.role = role;
			this.authorities = authorities;
		}
	}
}
