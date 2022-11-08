package scanner.prototype.middleware.token;


import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import scanner.prototype.exception.TokenValidFailedException;


public class TokenProvider {

    private final Key key;
    private static final String AUTHORITIES_KEY = "role";

    public TokenProvider(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Token createToken(String id, Date expiry) {
        return new Token(id, expiry, key);
    }

    public Token createToken(String id, String role, Date expiry) {
        return new Token(id, role, expiry, key);
    }

    public Token createToken(String id, String role, Date expiry, String username) {
        return new Token(id, role, expiry, key, username);
    }

    public Token convertToken(String token) {
        return new Token(token, key);
    }

    public Authentication getAuthentication(Token token) {

        if(token.validate()) {

            Claims claims = token.getTokenClaims();
            Collection<? extends GrantedAuthority> authorities = Arrays.stream(
                new String[]{claims.get(AUTHORITIES_KEY).toString()})
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            User principal = new User(claims.getSubject(), "", authorities);

            return new UsernamePasswordAuthenticationToken(principal, token, authorities);
        } 
        else {
            throw new TokenValidFailedException();
        }
    }
}
