package edu.gmu.cyse.gta;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

public class KeyGeneration {

	/**public static void main(String[] args) {
		  // Replace with your secret key
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Create JWT claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "gtasystem");
        claims.put("user_id", 12345678);

        // Set expiration time (in milliseconds)
        long now = System.currentTimeMillis();
        Date expiryDate = new Date(now + 1000 * 60 * 60 * 10); // 10 hours

        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        System.out.println("JWT Token: " + jwtToken);
    
	
        // Parse the token and validate the signature
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken);

        // Extract claims from the token
        Claims claims_ob = claimsJws.getBody();

        // Access claims data
        String subject = claims_ob.getSubject();
        Date expirationDate = claims_ob.getExpiration();
        // ... other claims ...

        System.out.println("Subject: " + subject);
        System.out.println("Expiration Date: " + expirationDate);
    }
	*/

}
