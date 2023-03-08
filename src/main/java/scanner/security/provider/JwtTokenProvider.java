package scanner.security.provider;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import scanner.exception.ApiException;
import scanner.model.enums.RoleType;
import scanner.common.enums.ResponseCode;
import scanner.model.user.User;
import scanner.security.config.Properties;
import scanner.security.dto.Token;
import scanner.security.dto.UsernameToken;
import scanner.security.service.CustomUserDetailService;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

	private static final long EXPIREDTIME = 3 * 24 * 60 * 60 * 1000L;

	private final Properties jwt;
	private final CustomUserDetailService userDetailsService;

	public Token create(String username, RoleType role, String key) {

		Date now = new Date();

		String accessToken = Jwts.builder()
			.setSubject(username)
			.claim("role", role)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + EXPIREDTIME))
			.signWith(SignatureAlgorithm.HS256, key)
			.compact();

		return new UsernameToken(username, accessToken, null);
	}

	public Claims getClaims(String token, String key) {
		try {
			return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		} catch (SecurityException e) {
			log.info("Invalid JWT signature", e);
		} catch (MalformedJwtException e) {
			log.info("Invalid JWT token", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty", e);
		} catch (Exception e) {
			log.info("Error occur on JWT", e);
		}

		return null;
	}

	public String getUsername(String token, String key) {
		return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
	}

	public String getUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ((User)principal).getUsername();
	}

	public Authentication getAuthentication(String token, String key) {

		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token, key));

		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getToken() {
		String username = getUsername();

		User requestUser = (User)userDetailsService.loadUserByUsername(username);

		return requestUser.getOAuthToken().getAccessToken();
	}

	public boolean validate(String token, String key) {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			return true;
		} catch (SecurityException e) {
			log.info("Invalid JWT signature", e);
		} catch (MalformedJwtException e) {
			log.info("Invalid JWT token", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty", e);
		} catch (Exception e) {
			log.info("Error occur on JWT", e);
		}

		return false;
	}

	public String resolve(HttpServletRequest request) {

		String authorization = request.getHeader("Authorization");

		if (authorization == null)
			return null;

		if (authorization.startsWith("Bearer "))
			return authorization.substring(7);

		if (authorization.startsWith("token "))
			return authorization.substring(6);

		throw new ApiException(ResponseCode.INVALID_TOKEN);
	}
}
