package scanner.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import scanner.dto.user.UserSignupDto;
import scanner.model.enums.RoleType;
import scanner.security.dto.Token;
import scanner.security.provider.JwtTokenProvider;

@AutoConfigureRestDocs
@SpringBootTest
class UserTokenTest {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Test
	@DisplayName("Password Validation Test")
	void validatePasswordTest() {

		// given
		String origin = "password";
		UserSignupDto registerDto = new UserSignupDto("user", origin, "email@addr.com", "010-0000-0000", null);
		PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();

		// when
		String result = registerDto.setHash(passwordEncoder, origin);

		// then
		Assertions.assertNotEquals(result, origin);
	}

	@Test
	@DisplayName("Token Creation Test")
	void createTokenTest() {

		// given
		String key = "test_key_test_key_test_key_test_key_test_key_test_key_test_key";
		String username = "test_username";

		// when
		Token token = jwtTokenProvider.create(username, RoleType.GUEST, key);

		// then
		Assertions.assertTrue(jwtTokenProvider.validate(token.getAccessToken(), key));
		Assertions.assertEquals("test_username", jwtTokenProvider.getUsername(token.getAccessToken(), key));
	}

	@Test
	@DisplayName("Token Creation Fail Test")
	void createTokenFailTest() {

		// give
		String key = "test_key_test_key_test_key_test_key_test_key_test_key_test_key";
		String failKey = "fail_key";
		String username = "test_username";

		// when
		Token token = jwtTokenProvider.create(username, RoleType.GUEST, key);

		// then
		Assertions.assertFalse(jwtTokenProvider.validate(token.getAccessToken(), failKey));
	}
}
