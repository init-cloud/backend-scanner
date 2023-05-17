package scanner.oauth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scanner.security.config.Properties;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthDto {

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class GithubTokenResponse {
		private String accessToken;
		private Long expiresIn;
		private String refreshToken;
		private Long refreshTokenExpiresIn;
		private String scope;
		private String tokenType;
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class GithubTokenRequest {
		private String clientId;
		private String clientSecret;
		private String code;
		private String redirectUri;

		public GithubTokenRequest(Properties properties, String code) {
			this.clientId = properties.getGithubClientId();
			this.clientSecret = properties.getGithubClientSecret();
			this.redirectUri = properties.getRedirectUri();
			this.code = code;
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class AuthCodeRequest {
		private String code;

		public AuthCodeRequest(String code) {
			this.code = code;
		}
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class GithubUserDetail {
		private String login;
		private Long id;
		private String avatarUrl;
		private String name;
	}
}
