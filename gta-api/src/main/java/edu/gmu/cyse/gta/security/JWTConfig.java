package edu.gmu.cyse.gta.security;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import jakarta.annotation.PostConstruct;

@SuppressWarnings("unused")
@Component
public class JWTConfig {
	private String secret;
    private long expirationMs;
    private long refreshExpirationMs;
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public long getExpirationMs() {
		return expirationMs;
	}
	public void setExpirationMs(long expirationMs) {
		this.expirationMs = expirationMs;
	}
	public long getRefreshExpirationMs() {
		return refreshExpirationMs;
	}
	public void setRefreshExpirationMs(long refreshExpirationMs) {
		this.refreshExpirationMs = refreshExpirationMs;
	}
 
	
	
	    
}
