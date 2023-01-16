package scanner.security.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Token {

    private String username;
    private String accessToken;
    private String refreshToken;
}
