package scanner.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OAuthProvider {
	NONE("NONE"), GITHUB("GITHUB");

	@Getter
	private final String provider;
}
