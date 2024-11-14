package edu.gmu.cyse.gta.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

@Slf4j
@Component
public class TokenProviderOld {

    //@Value("${app.jwt.secret}")
    
	@Autowired
    private JWTConfig jwtConfig;
	//private String jwtSecret;

    //@Value("${app.jwt.expiration.ms}")
    //private long jwtExpirationMs;

    //@Value("${app.jwt.refreshExpiration.ms}")
    //private long refreshExpirationMs;

    private static final String TOKEN_TYPE = "JWT";
    private static final String TOKEN_ISSUER = "order-api";
    private static final String TOKEN_AUDIENCE = "order-app";
    


    /**
     * Generates a JWT token based on the authenticated user's details.
     * 
     * @param authentication the authentication object containing user details.
     * @return the generated JWT token.
     */
    public String generateToken(Authentication authentication) {
    	
    	CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        //byte[] signingKey = jwtSecret.getBytes();
        byte[] signingKey = jwtConfig.getSecret().getBytes();


        return Jwts.builder()
                .setHeaderParam("typ", TOKEN_TYPE)  // Set the type directly using setHeaderParam
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
//                .setExpiration(Date.from(ZonedDateTime.now().plus(jwtExpirationMs, ChronoUnit.MILLIS).toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plus(jwtConfig.getExpirationMs(), ChronoUnit.MILLIS).toInstant()))
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setId(UUID.randomUUID().toString())
                .setIssuer(TOKEN_ISSUER)
                .setAudience(TOKEN_AUDIENCE)
                .setSubject(user.getUsername())
                .claim("rol", roles)
                .claim("name", user.getName())
                .claim("preferred_username", user.getUsername())
                .claim("email", user.getEmail())
                .compact();
    }
    
    private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();
        for(GrantedAuthority authority: authorities) {
            auths.add(authority.getAuthority());
        }
        return String.join(",",auths);
    }
    

    /**
     * Validates a JWT token and returns the claims.
     * 
     * @param token the JWT token.
     * @return an optional containing the claims if the token is valid.
     */
    /*public Optional<Jws<Claims>> validateTokenAndGetJws(String token) {
        try {
            //byte[] signingKey = jwtSecret.getBytes();
        	byte[] signingKey = jwtConfig.getSecret().getBytes();


            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);

            return Optional.of(jws);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException 
                | SignatureException | IllegalArgumentException e) {
            log.error("JWT validation failed for token: {}. Reason: {}", token, e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Generates a new access token using a refresh token.
     * 
     * @param refreshToken the refresh token.
     * @return the new access token.
     */
    /*public String generateTokenFromRefreshToken(String refreshToken) {
        Claims claims = getClaimsFromToken(refreshToken);

        // Validate if the refresh token is expired
        if (claims == null || isTokenExpired(claims)) {
            throw new RuntimeException("Refresh token is expired or invalid");
        }

        String username = claims.getSubject();
        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        Date expiryDate = new Date(now.getTime() + jwtConfig.getExpirationMs());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Extracts claims from a JWT token.
     * 
     * @param token the JWT token.
     * @return the claims, or null if parsing failed.
     */
    /*private Claims getClaimsFromToken(String token) {
        try {
            // Log the raw token for debugging purposes
            log.info("Raw JWT Token: {}", token);
            
            String cleanToken = null;

            // Manually decode base64-encoded JWT parts
            try {
            	cleanToken = token.trim(); // Remove leading/trailing whitespaces
            	//String[] parts = cleanToken.split("\\.");
                String[] parts = token.split("\\."); // Split the JWT into header, payload, and signature
                String header = new String(Base64.getUrlDecoder().decode(parts[0])); // Use Base64 URL decoder
                String payload = new String(Base64.getUrlDecoder().decode(parts[1])); // Use Base64 URL decoder
                byte[] signature = Base64.getUrlDecoder().decode(parts[2].replace("$", ""));

                log.info("Decoded Header: {}", header);
                log.info("Decoded Payload: {}", payload);
            } catch (IllegalArgumentException e) {
                log.error("Error decoding base64 token part: {}", e.getMessage());
            }

            // Parse the claims from the token using the correct parser
            Claims claims = Jwts.parserBuilder()
                    //.setSigningKey(jwtSecret)
            		.setSigningKey(jwtConfig.getSecret())
                    
                    .build()
                    .parseClaimsJws(token)  // This will throw exception if signature is invalid
                    .getBody();

            log.info("Claims extracted: {}", claims);
            return claims;
        } catch (Exception e) {
            log.error("Error parsing JWT: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Checks if a token is expired.
     * 
     * @param claims the claims of the token.
     * @return true if the token is expired, false otherwise.
     */
    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}
