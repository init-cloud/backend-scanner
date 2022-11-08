package scanner.prototype.middleware.token;


import java.security.Key;
import java.util.Date;
import java.util.logging.Logger;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class Token {

    private final static Logger log = Logger.getGlobal();
    private final String token;
    private final Key key;

    private static final String AUTHORITIES_KEY = "role";

    Token(String id, Date expiry, Key key) {
        this.key = key;
        this.token = createToken(id, expiry);
    }

    Token(String id, String role, Date expiry, Key key) {
        this.key = key;
        this.token = createToken(id, role, expiry);
    }

    Token(String id, String role, Date expiry, Key key, String name) {
        this.key = key;
        this.token = createToken(id, role, expiry, name);
    }

    private String createToken(String id, Date expiry) {
        return Jwts.builder()
                .setSubject(id)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    private String createToken(String id, String role, Date expiry) {           
        return Jwts.builder()
                .setSubject(id)
                .claim(AUTHORITIES_KEY, role)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    private String createToken(String id, String role, Date expiry, String username) {           
        return Jwts.builder()
                .setSubject(id)
                .claim(AUTHORITIES_KEY, role)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    public boolean validate() {
        return this.getTokenClaims() != null;
    }

    public Claims getTokenClaims() {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        }
        return null;
    }

    public Claims getExpiredTokenClaims() {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            return e.getClaims();
        }
        return null;
    }
}
