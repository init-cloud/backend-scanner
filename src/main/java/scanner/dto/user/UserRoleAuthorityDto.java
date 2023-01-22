package scanner.dto.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import scanner.model.enums.RoleType;

import java.util.Collection;

@Getter
public class UserRoleAuthorityDto extends UserDto {

    RoleType role;
    Collection<? extends GrantedAuthority> authorities;

    public UserRoleAuthorityDto(String username, RoleType role, Collection<? extends GrantedAuthority> authorities){
        super(username);
        this.role = role;
        this.authorities = authorities;
    }
}
