package scanner.security.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class Properties {

	private final Environment environment;

	@Getter
	private String secret;

	@Getter
	private String githubClientId;

	@Getter
	private String githubClientSecret;

	@Getter
	private String githubRedirectUri;

	@PostConstruct
	public void jwtInit() {
		this.secret = environment.getProperty("JWT_SECRET");
		this.githubClientId = environment.getProperty("GITHUB_CLIENT_ID");
		this.githubClientSecret = environment.getProperty("GITHUB_CLIENT_SECRET");
		this.githubRedirectUri = environment.getProperty("GITHUB_CALLBACK");
		log.info("JWT_SECRET is " + this.secret);
	}
}
