package edu.gmu.cyse.gta.security;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtConfigProvider {
    @Autowired
	JWTConfig jwtConfig;
	
    @PostConstruct
    public void initialize() {
        // Logic to generate your secret key (replace with your own logic)
        String generatedSecretKey = generateSecretKey();
        jwtConfig.setSecret(generatedSecretKey);
        
        // Set expiration times in milliseconds
        jwtConfig.setExpirationMs(86400000L); // 1 day
        jwtConfig.setRefreshExpirationMs(86400000L); // 1 day
    }

    private String generateSecretKey() {
    	@SuppressWarnings("deprecation")
		SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    	byte[] keyBytes = key.getEncoded();
		String base64EncodedKey = Base64.getEncoder().encodeToString(keyBytes);
		return base64EncodedKey;
    }

}
