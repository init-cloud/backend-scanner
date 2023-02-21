package scanner.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeaderParse {
	private static final String TOKEN_PREFIX = "Bearer ";

	public static String getAccessToken(String headerValue) {

		if (headerValue == null)
			return null;

		if (headerValue.startsWith(TOKEN_PREFIX))
			return headerValue.substring(TOKEN_PREFIX.length());

		return null;
	}
}