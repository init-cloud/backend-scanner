package scanner.user.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.stereotype.Service;

import scanner.user.dto.UserAuthenticationDto;
import scanner.user.dto.UserDto;
import scanner.user.dto.UserSignupDto;
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
