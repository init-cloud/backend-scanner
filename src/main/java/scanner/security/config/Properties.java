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

	@PostConstruct
	public void jwtInit() {
		this.secret = environment.getProperty("JWT_SECRET");
		log.info("JWT_SECRET is " + this.secret);
	}
}
