package scanner.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scanner.dto.user.UserAuthenticationDto;
import scanner.dto.user.UserSignupDto;
import scanner.model.User;
import scanner.model.enums.RoleType;
import scanner.repository.UserRepository;
import scanner.security.dto.Token;
import scanner.security.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class UsernameService implements UserService{
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Token signup(UserSignupDto dto){

        User user = userRepository.save(User.toEntity(dto));

        return jwtTokenProvider.create(user.getUsername(), RoleType.GUEST);
    }

    @Override
    public Token signin(UserAuthenticationDto dto){

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid User."));

        return jwtTokenProvider.create(user.getUsername(), user.getRoleType());
    }
}
