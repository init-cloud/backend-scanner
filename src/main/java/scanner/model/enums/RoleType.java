package scanner.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RoleType {
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER"),
	GUEST("ROLE_GUEST");

	private final String role;

	public static RoleType of(String code) {

		return Arrays.stream(RoleType.values())
			.filter(r -> r.getRole().equals(code))
			.findAny()
			.orElse(GUEST);
	}
}