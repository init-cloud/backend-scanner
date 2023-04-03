package scanner.security.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class Token {
	private String accessToken;
	private String refreshToken;
}
