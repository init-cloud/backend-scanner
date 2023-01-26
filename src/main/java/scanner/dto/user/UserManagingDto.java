package scanner.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import scanner.model.enums.RoleType;
import scanner.model.enums.UserState;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserManagingDto extends UserRetrieveDto {

    Collection<? extends GrantedAuthority> authorities;

    public UserManagingDto(String username,
                           UserState userState,
                           RoleType role,
                           Collection<? extends GrantedAuthority> authorities,
                           LocalDateTime lastLogin
    ){
        super(username, userState, role, lastLogin);
        this.authorities = authorities;
    }
}
