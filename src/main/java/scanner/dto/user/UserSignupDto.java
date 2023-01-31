package scanner.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignupDto extends UserProfileDto{

    private String password;

    public UserSignupDto(String username,
                         String password,
                         String email,
                         String contact,
                         LocalDateTime lastLogin
    ){
        super(username, email, contact, lastLogin);
        this.password = password;
    }
}
