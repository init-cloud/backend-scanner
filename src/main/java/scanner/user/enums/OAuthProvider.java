package scanner.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OAuthProvider {
	NONE("NONE"), GITHUB("GITHUB");

	private String provider;
}
