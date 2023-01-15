package scanner.security.jwt;


import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import scanner.security.service.CustomUserDetailService;
import java.security.Key;
import java.util.Date;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final long expiredTime = 60 * 60 * 1000L;
    private final Key key;
    private final CustomUserDetailService userDetailsService;

    public String create(String username) {

        Date now = new Date();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiredTime))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Claims getClaims(final String token){
        try {
            return Jwts.parser()
                    .setSigningKey(key)
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
            log.info("JWT claims string is empty.", e);
        } catch (Exception e){
            log.info("Error occur on JWT.", e);
        }

        return null;
    }

    public boolean validate(final String token) {
        try {
            Jwts.parser()
                .setSigningKey(key)
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
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
