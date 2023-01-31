package scanner.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scanner.model.User;
import scanner.model.enums.RoleType;
import scanner.model.enums.UserState;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRetrieveDto extends UserDto{

    private UserState userState;
    private RoleType role;

    public UserRetrieveDto(String username,
                           UserState userState,
                           RoleType role,
                           LocalDateTime lastLogin
    ){
        super(username, lastLogin);
        this.userState = userState;
        this.role = role;
    }

    public UserRetrieveDto(User user){
        super(user.getUsername(), user.getLastLogin());
        this.userState = user.getUserState();
        this.role = user.getRoleType();
    }
}
