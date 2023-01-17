package scanner.security.config;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class JwtProperties{

    private final Environment environment;

    @Getter
    private String secret;

    @PostConstruct
    public void init(){
        this.secret = environment.getProperty("JWT_SECRET");
    }
}
