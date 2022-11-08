package scanner.prototype.configuration;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "app")
public class AppConfigProperties {

    private final Auth auth = new Auth();

    @NoArgsConstructor
    @AllArgsConstructor
    public static class Auth {
        private String tokenSecret;
        private long tokenExpiry;
        private long refreshTokenExpiry;
    }
}
