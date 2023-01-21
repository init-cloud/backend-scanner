package scanner.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import scanner.dto.user.UserAuthenticationDto;
import scanner.dto.user.UserDto;
import scanner.dto.user.UserSignupDto;
import scanner.model.User;
import scanner.model.enums.RoleType;
import scanner.repository.UserRepository;
import scanner.security.dto.Token;
import scanner.security.provider.JwtTokenProvider;
import scanner.security.provider.UsernamePasswordAuthenticationProvider;

@Service
@RequiredArgsConstructor
public class UsernameService implements UserService{
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Override
    public Token signup(UserSignupDto dto){

        User user = userRepository.save(User.toEntity(dto));

        return jwtTokenProvider.create(user.getUsername(), RoleType.GUEST);
    }

    @Override
    public Token signin(UserAuthenticationDto dto){

        Authentication authentication = usernamePasswordAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.create(
            dto.getUsername(),
            ((User) authentication.getPrincipal()).getRoleType());
    }
}
