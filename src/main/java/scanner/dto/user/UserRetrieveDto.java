package scanner.dto.user;

import lombok.Getter;
import scanner.model.User;
import scanner.model.enums.RoleType;
import scanner.model.enums.UserState;


@Getter
public class UserRetrieveDto extends UserDto{

    UserState userState;
    RoleType role;

    public UserRetrieveDto(String username,
                           UserState userState,
                           RoleType role){
        super(username);
        this.userState = userState;
        this.role = role;
    }

    public UserRetrieveDto(User user){
        super(user.getUsername());
        this.userState = user.getUserState();
        this.role = user.getRoleType();
    }
}
