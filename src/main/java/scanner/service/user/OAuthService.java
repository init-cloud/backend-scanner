package scanner.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scanner.dto.user.UserAuthenticationDto;
import scanner.dto.user.UserSignupDto;
import scanner.repository.UserRepository;
import scanner.security.dto.Token;

@Service
@RequiredArgsConstructor
public class OAuthService implements UserService{
    private final UserRepository userRepository;

    @Override
    public Token signup(UserSignupDto dto) throws Exception{
        return null;
    }

    @Override
    public Token signin(UserAuthenticationDto dto) throws Exception{
        return null;
    }
}
