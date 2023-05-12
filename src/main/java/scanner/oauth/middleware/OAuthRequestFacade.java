package scanner.oauth.middleware;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import scanner.common.client.OAuthFeignClient;
import scanner.common.client.OAuthInfoFeignClient;
import scanner.common.enums.ResponseCode;
import scanner.common.exception.ApiAuthException;
import scanner.oauth.dto.OAuthDto;
import scanner.security.config.Properties;
import scanner.security.dto.Token;
import scanner.security.provider.JwtTokenProvider;

@Component
@RequiredArgsConstructor
public class OAuthRequestFacade {

	private final OAuthFeignClient oauthFeignClient;
	private final OAuthInfoFeignClient infoFeignClient;
	private final JwtTokenProvider jwtTokenProvider;
	private final Properties properties;

	private static final String GITHUB_AUTH_URL = "https://github.com/login/oauth/authorize?";

	/**
	 * Request Github Access Token by auth code.
	 * @return access token in String
	 */
	public String requestGithubOAuthToken(String code) {
		OAuthDto.GithubTokenRequest tokenRequest = new OAuthDto.GithubTokenRequest(properties, code);
		String resultString = oauthFeignClient.requestGithubAccessToken(tokenRequest);

		String[] arr = resultString.split("&");

		for (String s : arr) {
			if (s.startsWith("access_token"))
				return s;
		}

		throw new ApiAuthException(ResponseCode.INVALID_TOKEN);
	}

	/**
	 * Request Github User info by access token.
	 * @return user detail in OAuthDto.GithubUserDetail
	 */
	public OAuthDto.GithubUserDetail requestGithubUserDetail(String accessToken) {
		return infoFeignClient.requestGithubUserDetail("Bearer " + accessToken.split("=")[1]);
	}

	/**
	 * You can get user access token
	 * @return user access token in Token
	 */
	public Token createSocialUserToken(String username) {
		return jwtTokenProvider.createPersonalSocialUser(username, properties.getSecret());
	}

	public String getRedirectAuthUrl(String redirect) {
		return GITHUB_AUTH_URL + "client_id=" + properties.getGithubClientId() + "&redirect_uri=" + redirect;
	}
}
