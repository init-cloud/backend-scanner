package scanner.security.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UsernameToken extends Token {

	private String username;

	public UsernameToken(String username, String accessToken, String refreshToken) {
		super(accessToken, refreshToken);
		this.username = username;
	}
}
