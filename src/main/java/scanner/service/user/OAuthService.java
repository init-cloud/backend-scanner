package scanner.service.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.stereotype.Service;

import scanner.dto.user.UserAuthenticationDto;
import scanner.dto.user.UserDto;
import scanner.dto.user.UserSignupDto;
import scanner.security.dto.Token;

@Service
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthService implements UserService {

	@Override
	public Token signup(UserSignupDto dto) {
		return null;
	}

	@Override
	public Token signin(UserAuthenticationDto dto) {
		return null;
	}

	@Override
	public void updateLastLogin(UserDto user) {
		//
	}
}
