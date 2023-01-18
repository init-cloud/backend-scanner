package scanner.security.jwt;


import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import scanner.model.enums.RoleType;
import scanner.security.config.JwtProperties;
import scanner.security.dto.Token;
import scanner.security.service.CustomUserDetailService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final long expiredTime = 3 * 24 * 60 * 60 * 1000L;

    private final JwtProperties jwt;
    private final CustomUserDetailService userDetailsService;

    public Token create(String username, RoleType role) {

        Date now = new Date();

        String accessToken = Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiredTime))
                .signWith(SignatureAlgorithm.HS256, jwt.getSecret())
                .compact();

        return new Token(username, accessToken, null);
    }

    public Claims getClaims(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(jwt.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException e) {
            log.info("Invalid JWT signature", e);
        } catch (MalformedJwtException e){
            log.info("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty", e);
        } catch (Exception e){
            log.info("Error occur on JWT", e);
        }

        return null;
    }

    private String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(jwt.getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Authentication getAuthentication(String token) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validate(String token) {
        try {
            Jwts.parser()
                .setSigningKey(jwt.getSecret())
                .parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            log.info("Invalid JWT signature", e);
        } catch (MalformedJwtException e){
            log.info("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty", e);
        } catch (Exception e){
            log.info("Error occur on JWT", e);
        }

        return false;
    }
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwt.getSecret())
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private String extract(String rawToken){
        if(rawToken == null)
            return null;

        if(!rawToken.startsWith("Bearer "))
            throw new IllegalArgumentException();

        return rawToken.substring("Bearer ".length());
    }

    public String resolve(HttpServletRequest request) {
        return this.extract(request.getHeader("Authorization"));
    }
}
