package scanner.oauth.middleware;

import lombok.RequiredArgsConstructor;
import scanner.common.client.OAuthFeignClient;
import scanner.common.client.OAuthInfoFeignClient;
import scanner.oauth.dto.OAuthDto;
import scanner.security.config.Properties;
import scanner.security.dto.Token;
import scanner.security.provider.JwtTokenProvider;

@RequiredArgsConstructor
public class OAuthRequestFacade {

	private final OAuthFeignClient oauthFeignClient;
	private final OAuthInfoFeignClient infoFeignClient;
	private final JwtTokenProvider jwtTokenProvider;
	private final Properties properties;

	/**
	 * Request Github Access Token by auth code.
	 * @return access token in OAuthDto.GithubTokenResponse
	 */
	public OAuthDto.GithubTokenResponse requestGithubOAuthToken(String code) {
		OAuthDto.GithubTokenRequest tokenRequest = new OAuthDto.GithubTokenRequest(properties.getGithubClientSecret(), code);
		return oauthFeignClient.requestGithubAccessToken(tokenRequest);
	}

	/**
	 * Request Github User info by access token.
	 * @return user detail in OAuthDto.GithubUserDetail
	 */
	public OAuthDto.GithubUserDetail requestGithubUserDetail(String accessToken) {
		return infoFeignClient.requestGithubUserDetail("Bearer " + accessToken);
	}

	/**
	 * You can get user access token
	 * @return user access token in Token
	 */
	public Token createSocialUserToken(String username) {
		return jwtTokenProvider.createPersonalSocialUser(username, properties.getSecret());
	}
}
