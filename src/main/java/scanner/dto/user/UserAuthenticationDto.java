package scanner.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAuthenticationDto extends UserDto {

	private String password;

	public UserAuthenticationDto(String username, String password, LocalDateTime lastLogin) {
		super(username, lastLogin);
		this.password = password;
	}
}
