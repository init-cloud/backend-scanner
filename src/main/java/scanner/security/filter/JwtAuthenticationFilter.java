package scanner.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import scanner.security.config.Properties;
import scanner.security.provider.JwtTokenProvider;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final Properties properties;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {

		String token = jwtTokenProvider.resolve((HttpServletRequest)request);

		if (token == null)
			SecurityContextHolder.getContext().setAuthentication(null);

		else if (jwtTokenProvider.validate(token, properties.getSecret())) {
			Authentication authentication = jwtTokenProvider.getAuthentication(token, properties.getSecret());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		chain.doFilter(request, response);
	}
}

