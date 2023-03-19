package scanner.user.service;

import scanner.user.dto.UserAuthenticationDto;
import scanner.user.dto.UserDto;
import scanner.user.dto.UserSignupDto;
import scanner.security.dto.Token;

public interface UserService {

	Token signup(UserSignupDto dto);

	Token signin(UserAuthenticationDto dto);

	void updateLastLogin(UserDto user);
}
