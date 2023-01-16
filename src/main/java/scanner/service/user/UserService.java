package scanner.service.user;

import scanner.dto.user.UserAuthenticationDto;
import scanner.dto.user.UserSignupDto;
import scanner.security.dto.Token;

public interface UserService {

    public Token signup(UserSignupDto dto) throws Exception;
    public Token signin(UserAuthenticationDto dto) throws Exception;
}
