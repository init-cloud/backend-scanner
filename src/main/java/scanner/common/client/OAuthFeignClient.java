package scanner.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import scanner.oauth.dto.OAuthDto;

@FeignClient(name = "feignClient", contextId = "oauthFeignClient", url = "https://github.com")
public interface OAuthFeignClient {

	@PostMapping(value = "/login/oauth/access_token")
	String requestGithubAccessToken(@RequestBody OAuthDto.GithubTokenRequest tokenRequest);
}
