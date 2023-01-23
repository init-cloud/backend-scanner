package scanner.dto.user;


import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class UserAuthenticationDto extends UserDto{

    private final String password;

    public UserAuthenticationDto(String username, String password, LocalDateTime lastLogin) {
        super(username, lastLogin);
        this.password = password;
    }
}
