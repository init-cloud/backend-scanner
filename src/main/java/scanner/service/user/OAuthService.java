package scanner.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scanner.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class OAuthService implements UserService{
    private final UserRepository userRepository;

    @Override
    public Long signup() throws Exception{
        return null;
    }

    @Override
    public Long signin() throws Exception{
        return null;
    }
}
