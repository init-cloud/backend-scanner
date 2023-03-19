package scanner.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scanner.user.entity.User;
import scanner.user.enums.RoleType;
import scanner.user.enums.UserState;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRetrieveDto extends UserDto {

	private UserState userState;
	private RoleType role;

	public UserRetrieveDto(String username, UserState userState, RoleType role, LocalDateTime lastLogin) {
		super(username, lastLogin);
		this.userState = userState;
		this.role = role;
	}

	public UserRetrieveDto(User user) {
		super(user.getUsername(), user.getLastLogin());
		this.userState = user.getUserState();
		this.role = user.getRoleType();
	}
}
