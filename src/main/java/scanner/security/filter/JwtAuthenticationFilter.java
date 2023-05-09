package scanner.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;

import scanner.common.enums.ResponseCode;
import scanner.common.exception.ApiAuthException;
import scanner.security.config.Properties;
import scanner.security.provider.JwtTokenProvider;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final String EXCEPTION = "exception";
	private final JwtTokenProvider jwtTokenProvider;
	private final Properties properties;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		IOException, ServletException {

		try {
			String token = jwtTokenProvider.resolve(request);

			if (token == null)
				throw new NullPointerException();

			if (!jwtTokenProvider.validate(token, properties.getSecret()))
				throw new ApiAuthException(ResponseCode.INVALID_TOKEN);

			Authentication authentication = jwtTokenProvider.getAuthentication(token, properties.getSecret());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (NullPointerException e) {
			request.setAttribute(EXCEPTION, ResponseCode.NULL_TOKEN.getCode());
		} catch (SecurityException e) {
			request.setAttribute(EXCEPTION, ResponseCode.INVALID_TOKEN_SIGNATURE.getCode());
		} catch (MalformedJwtException e) {
			request.setAttribute(EXCEPTION, ResponseCode.INVALID_TOKEN_FORMAT.getCode());
		} catch (ExpiredJwtException e) {
			request.setAttribute(EXCEPTION, ResponseCode.TOKEN_EXPIRED.getCode());
		} catch (UnsupportedJwtException e) {
			request.setAttribute(EXCEPTION, ResponseCode.UNSUPPORTED_TOKEN.getCode());
		} catch (IllegalArgumentException e) {
			request.setAttribute(EXCEPTION, ResponseCode.EMPTY_TOKEN_CLAIMS.getCode());
		} catch (Exception e) {
			request.setAttribute(EXCEPTION, ResponseCode.INVALID_TOKEN.getCode());
		}

		chain.doFilter(request, response);
	}
}

