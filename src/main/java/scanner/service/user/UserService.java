package scanner.service.user;

import scanner.dto.user.UserAuthenticationDto;
import scanner.dto.user.UserDto;
import scanner.dto.user.UserSignupDto;
import scanner.security.dto.Token;

public interface UserService {

	Token signup(UserSignupDto dto);

	Token signin(UserAuthenticationDto dto);

	void updateLastLogin(UserDto user);
}
