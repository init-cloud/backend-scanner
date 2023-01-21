package scanner.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import scanner.exception.ApiException;
import scanner.response.enums.ResponseCode;


@Getter
@RequiredArgsConstructor
public class UserSignupDto extends UserDto{

    private String password;
    private String email;
    private String contact;

    public void setHash(PasswordEncoder encoder){

        if(password.length() < 8 || password.length() > 32)
            throw new ApiException(ResponseCode.STATUS_4009);

        this.password = encoder.encode(this.password);
    }
}
