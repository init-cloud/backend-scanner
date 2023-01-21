package scanner.dto.user;


import lombok.Getter;

@Getter
public class UserAuthenticationDto extends UserDto{

    private final String password;

    public UserAuthenticationDto(String username, String password) {
        super(username);
        this.password = password;
    }
}
