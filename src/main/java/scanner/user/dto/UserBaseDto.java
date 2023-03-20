package scanner.user.dto;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import scanner.common.enums.ResponseCode;
import scanner.common.exception.ApiException;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class UserBaseDto {

	private String username;
	private LocalDateTime lastLogin;

	public String setHash(PasswordEncoder encoder, String password) {

		if (password.length() < 8 || password.length() > 32)
			throw new ApiException(ResponseCode.INVALID_PASSWORD_LENGTH);

		return encoder.encode(password);
	}

}
