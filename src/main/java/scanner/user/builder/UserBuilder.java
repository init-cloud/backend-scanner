package scanner.user.builder;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import scanner.user.entity.User;
import scanner.user.enums.RoleType;
import scanner.user.enums.UserState;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBuilder {

	public static User toEntityByModifying(User user, LocalDateTime lastLogin, String password, String authorities,
		RoleType roleType, UserState userState, String email, String contact) {
		return User.userModifyBuilder()
			.user(user)
			.lastLogin(lastLogin)
			.password(password)
			.roleType(roleType)
			.authorities(authorities)
			.userState(userState)
			.email(email)
			.contact(contact)
			.build();
	}
}
