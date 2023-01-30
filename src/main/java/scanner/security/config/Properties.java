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
    private String appClientId;
    @Getter
    private String appClientSecret;

    public final static String TMP_RANDOM = "f134g134g13gqwefeqwfqwfq";

    @PostConstruct
    public void jwtInit(){
        this.secret = environment.getProperty("JWT_SECRET");
        this.appClientId = environment.getProperty("GITHUB_APP_CLIENT_ID");
        this.appClientSecret = environment.getProperty("GITHUB_APP_CLIENT_SECRET");

        log.info("JWT_SECRET is " + this.secret);
        log.info("GITHUB_APP_CLIENT_ID is " + this.appClientId);
        log.info("GITHUB_APP_CLIENT_SECRET is " + this.appClientSecret);
    }
}
