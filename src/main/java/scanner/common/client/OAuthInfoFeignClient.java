package scanner.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import scanner.oauth.dto.OAuthDto;

@FeignClient(name = "feignClient", contextId = "oauthInfoFeignClient", url = "https://api.github.com")
public interface OAuthInfoFeignClient {

	@GetMapping(value = "/user")
	OAuthDto.GithubUserDetail requestGithubUserDetail(@RequestHeader("Authorization") String token);
}
