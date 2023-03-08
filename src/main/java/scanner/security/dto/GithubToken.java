package scanner.security.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GithubToken extends Token {

	private Long expiresIn;
	private Long refreshTokenExpiresIn;
	private String scope;
	private String tokenType;

	@Builder
	public GithubToken(String accessToken, String refreshToken, Long expiresIn, Long refreshTokenExpiresIn,
		String scope, String tokenType) {
		super(accessToken, refreshToken);
		this.expiresIn = expiresIn;
		this.refreshTokenExpiresIn = refreshTokenExpiresIn;
		this.scope = scope;
		this.tokenType = tokenType;
	}
}
