package scanner.dto.user;

import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import scanner.exception.ApiException;
import scanner.response.enums.ResponseCode;


@Getter
public class UserSignupDto extends UserDto{

    private String password;
    private final String email;
    private final String contact;

    public UserSignupDto(String username, String password, String email, String contact) {
        super(username);
        this.password = password;
        this.email = email;
        this.contact = contact;
    }

    public void setHash(PasswordEncoder encoder){

        if(password.length() < 8 || password.length() > 32)
            throw new ApiException(ResponseCode.STATUS_4009);

        this.password = encoder.encode(this.password);
    }
}
