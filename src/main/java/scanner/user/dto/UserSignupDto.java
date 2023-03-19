package scanner.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scanner.user.enums.RoleType;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignupDto extends UserProfileDto {

	private String password;

	public UserSignupDto(String username, String password, String email, String contact, LocalDateTime lastLogin) {
		super(username, email, contact, RoleType.GUEST, lastLogin);
		this.password = password;
	}
}
