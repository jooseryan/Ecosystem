package br.ufpb.ecosystem.security;

import br.ufpb.ecosystem.entities.User;
import br.ufpb.ecosystem.entities.UserRole;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * Service for handling JWT token generation and validation.
 */
@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Generates a JWT token for the authenticated user.
     *
     * @param user the user for whom the token is generated
     * @return a signed JWT token string
     */
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            List<String> roles = user.getRoles().stream()
                    .map(UserRole::getRole)
                    .toList();

            return JWT.create()
                    .withIssuer("auth-ecosystem")
                    .withSubject(user.getUsername())
                    .withClaim("roles", roles)
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to generate JWT token", ex);
        }
    }


    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-ecosystem")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            // You may log this exception for audit/debugging purposes
            return null;
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00")); // Consider making offset configurable
    }
}
