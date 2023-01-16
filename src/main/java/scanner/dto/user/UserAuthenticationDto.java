package scanner.dto.user;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserAuthenticationDto extends UserDto{
    private String password;
}
