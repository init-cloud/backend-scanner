package scanner.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scanner.user.enums.RoleType;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileDto extends UserDto {

	@Setter
	private String email;

	@Setter
	private String contact;

	private RoleType role;

	public UserProfileDto(String username, String email, String contact, RoleType role, LocalDateTime lastLogin) {
		super(username, lastLogin);
		this.role = role;
		this.email = email;
		this.contact = contact;
	}
}
