package scanner.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import scanner.exception.ApiException;
import scanner.response.enums.ResponseCode;

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

    public void setHash(PasswordEncoder encoder){

        if(password.length() < 8 || password.length() > 32)
            throw new ApiException(ResponseCode.STATUS_4009);

        this.password = encoder.encode(this.password);
    }
}
