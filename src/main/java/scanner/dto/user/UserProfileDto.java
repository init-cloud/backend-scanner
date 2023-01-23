package scanner.dto.user;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
public class UserProfileDto extends UserDto{

    @Setter
    private String email;

    @Setter
    private String contact;

    public UserProfileDto(String username,
                          String email,
                          String contact,
                          LocalDateTime lastLogin
    ){
        super(username, lastLogin);
        this.email = email;
        this.contact = contact;
    }
}
