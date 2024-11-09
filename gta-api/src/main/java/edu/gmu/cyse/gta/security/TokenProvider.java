package edu.gmu.cyse.gta.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration.minutes}")
    private Long jwtExpirationMinutes;
    
    @Value("${app.jwt.expiration.ms}")
    private long jwtExpirationMs;
    
    @Value("${app.jwt.refreshExpiration.ms}")
    private long refreshExpirationMs;

    public String generate(Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        byte[] signingKey = jwtSecret.getBytes();

        return Jwts.builder()
                .header().add("typ", TOKEN_TYPE)
                .and()
                .signWith(Keys.hmacShaKeyFor(signingKey), Jwts.SIG.HS512)
                .expiration(Date.from(ZonedDateTime.now().plusMinutes(jwtExpirationMs).toInstant()))
//                .expiration(Date.from(ZonedDateTime.now().plusMinutes(jwtExpirationMinutes).toInstant()))
                .issuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .id(UUID.randomUUID().toString())
                .issuer(TOKEN_ISSUER)
                .audience().add(TOKEN_AUDIENCE)
                .and()
                .subject(user.getUsername())
                .claim("rol", roles)
                .claim("name", user.getName())
                .claim("preferred_username", user.getUsername())
                .claim("email", user.getEmail())
                .compact();
    }

    public Optional<Jws<Claims>> validateTokenAndGetJws(String token) {
        try {
            byte[] signingKey = jwtSecret.getBytes();

            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(signingKey))
                    .build()
                    .parseSignedClaims(token);

            return Optional.of(jws);
        } catch (ExpiredJwtException exception) {
            log.error("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.error("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.error("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
        } catch (SignatureException exception) {
            log.error("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.error("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
        }
        return Optional.empty();
    }
    
    
    public String generateTokenFromRefreshToken(String refreshToken) {
        Claims claims = getClaimsFromToken(refreshToken);

        // Validate if the refresh token is expired
        if (claims == null || isTokenExpired(claims)) {
            throw new RuntimeException("Refresh token is expired or invalid");
        }

        // Extract the user information from the refresh token claims
        String username = claims.getSubject();

        // Generate a new access token
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    
    private Claims getClaimsFromToken(String token) {
        try {
            return ((JwtParser) Jwts.parser()
                    .setSigningKey(jwtSecret))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // Helper method to check if a token is expired
    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "order-api";
    public static final String TOKEN_AUDIENCE = "order-app";
}
