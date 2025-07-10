package com.cts.security;
 
import com.cts.exceptions.CustomerServiceException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
 
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

// Provider for generating and validating JWT tokens 
@Component
public class JwtTokenProvider {
    
    // Secret key for signing JWT tokens, loaded from application properties 
    @Value("${app.jwt.secret}")
    private String secret;
    
    // Expiration time for JWT tokens in milliseconds 
    @Value("${app.jwt.expiry_millis}")
    private long expirationTime;
 
    // Generates a JWT token for the authenticated user 
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + expirationTime);
        
        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(key())
                .compact();
    }
 
    // Retrieves the signing key for JWT tokens 
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
 
    // Extracts the username from the given JWT token 
    public String getName(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
 
    // Validates a given JWT token 
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) key())
                    .build()
                    .parse(token);
            return true;
        }
        catch (MalformedJwtException | SignatureException ex) {
            throw new CustomerServiceException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } 
        catch (ExpiredJwtException ex) {
            throw new CustomerServiceException(HttpStatus.BAD_REQUEST, "JWT token is expired");
        } 
        catch (UnsupportedJwtException ex) {
            throw new CustomerServiceException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } 
        catch (IllegalArgumentException ex) {
            throw new CustomerServiceException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        }
    }
}
