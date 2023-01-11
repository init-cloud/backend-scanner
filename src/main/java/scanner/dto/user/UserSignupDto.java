package scanner.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserSignupDto {

    private Long id;
    private String username;
    private String password;
}
