package edu.gmu.cyse.gta.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

@Slf4j
@Component
public class TokenProvider {

    //@Value("${app.jwt.secret}")
    
	    @SuppressWarnings("deprecation")
		public static SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    /**
     * Generates a JWT token based on the authenticated user's details.
     * 
     * @param authentication the authentication object containing user details.
     * @return the generated JWT token.
     */
    public String generateToken(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String roles = populateAuthorities(authorities);

        @SuppressWarnings("deprecation")
        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+86400000))
                .claim("email", authentication.getName())
                .claim( "authorities",roles)
                .signWith(key)
                .compact();
        System.out.println("Token for parsing in JwtProvider: " + jwt);
        return jwt;    	
    }
    
    private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();
        for(GrantedAuthority authority: authorities) {
            auths.add(authority.getAuthority());
        }
        return String.join(",",auths);
    }
    
    @SuppressWarnings("deprecation")
	public static String generateTokenFromRefreshToken(String jwt) {
        try {
            // Step 1: Parse the existing JWT token using the new JwtParserBuilder
        	Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            // Step 2: Extract current claims
            String email = claims.get("email", String.class);
            String authorities = claims.get("authorities", String.class);

            // Step 3: Generate a new token with the same claims but a new expiration date
            String renewedToken = Jwts.builder()
                    .setIssuedAt(new Date()) // New issue date
                    .setExpiration(new Date(new Date().getTime() + 86400000)) // New expiration date
                    .claim("email", email)
                    .claim("authorities", authorities)
                    .signWith(key)
                    .compact();

            return renewedToken;
        } catch (Exception e) {
            System.err.println("Error renewing token: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    @SuppressWarnings("deprecation")
    public static String getEmailFromJwtToken(String jwt) {
        jwt = jwt.substring(7); // Assuming "Bearer " is removed from the token
        try {
            //Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            String email = String.valueOf(claims.get("email"));
            System.out.println("Email extracted from JWT: " + claims);
            return email;
        } catch (Exception e) {
            System.err.println("Error extracting email from JWT: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

   
}
