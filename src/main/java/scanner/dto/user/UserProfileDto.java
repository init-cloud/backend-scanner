package scanner.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
