package scanner.user.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.stereotype.Service;

import scanner.user.dto.UserAuthDto;
import scanner.user.dto.UserBaseDto;
import scanner.security.dto.Token;
import scanner.user.entity.User;

@Service
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthService implements UserService {

	@Override
	public Token signup(UserAuthDto.Signup dto) {
		return null;
	}

	@Override
	public Token signin(UserAuthDto.Authentication dto) {
		return null;
	}

	@Override
	public User getCurrentUser() {
		return null;
	}

	@Override
	public void updateLastLogin(UserBaseDto user) {
	}
}
