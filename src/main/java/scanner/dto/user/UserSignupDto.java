package scanner.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserSignupDto extends UserDto{

    private String password;
    private String email;
    private String contact;
}
