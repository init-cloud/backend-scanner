package scanner.auth;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import scanner.user.dto.UserAuthDto;

@SpringBootTest
class UserTokenUnitTest {

	@Test
	@DisplayName("Password Validation Test")
	void validatePasswordTest() {
		UserAuthDto.Signup registerDto = new UserAuthDto.Signup("user", "password", LocalDateTime.now(),
			"email@addr.com", "010-1234-5678", null);
		PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();
		String origin = "password";

		String result = registerDto.setHash(passwordEncoder, origin);

		Assertions.assertNotEquals(result, origin);
	}
}
