package scanner.dto.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;

import scanner.common.enums.ResponseCode;
import scanner.exception.ApiException;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class UserDto {
	private String username;
	private LocalDateTime lastLogin;

	public String setHash(PasswordEncoder encoder, String password) {

		if (password.length() < 8 || password.length() > 32)
			throw new ApiException(ResponseCode.INVALID_PASSWORD_LENGTH);

		return encoder.encode(password);
	}
}
