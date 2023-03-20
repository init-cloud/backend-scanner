package scanner.user.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scanner.user.enums.RoleType;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAuthDto {

	@Getter
	public static class Authentication extends UserBaseDto {
		private final String password;

		public Authentication(String username, String password, LocalDateTime lastLogin) {
			super(username, lastLogin);
			this.password = password;
		}
	}

	@Getter
	public static class Signup extends UserBaseDto {
		private final String password;
		private final String email;
		private final String contact;
		private final RoleType role;

		public Signup(String username, String password, LocalDateTime lastLogin, String email, String contact,
			RoleType role) {
			super(username, lastLogin);
			this.email = email;
			this.contact = contact;
			this.role = role;
			this.password = password;
		}
	}
}
