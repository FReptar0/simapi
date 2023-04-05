package mx.utez.simapi.security.jwt;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import mx.utez.simapi.models.UserDetailImpl;

@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication) {
        UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
        return Jwts.builder()
                .signWith(getKey(secret))
                .setSubject(userDetail.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration + 1000))
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey(secret))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getKey(secret)).build().parseClaimsJws(token).getBody();
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("Token expired");
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            logger.error("Token not supported");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            logger.error("Token is empty");
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            logger.error("Token is malformed");
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("Token not valid");
            e.printStackTrace();
        }
        return false;
    }

    private Key getKey(String secret) {
        byte[] apiKeySecretBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(apiKeySecretBytes);
    }
}
