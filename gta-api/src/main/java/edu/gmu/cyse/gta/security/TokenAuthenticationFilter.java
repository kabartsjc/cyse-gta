package edu.gmu.cyse.gta.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKey;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader(JwtConstant.JWT_HEADER);
        System.out.println("JWT Token in JwtTokenValidator: " + jwt);
        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
            
            System.out.println("JWT Token in JwtTokenValidator: " + jwt);
            try {
                SecretKey key = Keys.hmacShaKeyFor(TokenProvider.key.getEncoded());
                @SuppressWarnings("deprecation")
                Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
                System.out.print(claims);

                String email = String.valueOf(claims.get("email"));
                System.out.print(email);
                String authorities = String.valueOf(claims.get("authorities"));
                List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auth);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                throw new BadCredentialsException("Invalid token", e);
            }
        }

        filterChain.doFilter(request, response);
    }
    
	/*
	 * private Optional<String> getJwtFromRequest(HttpServletRequest request) {
	 * String tokenHeader = request.getHeader(TOKEN_HEADER); if
	 * (StringUtils.hasText(tokenHeader) && tokenHeader.startsWith(TOKEN_PREFIX)) {
	 * return Optional.of(tokenHeader.replace(TOKEN_PREFIX, "")); } return
	 * Optional.empty(); }
	 */

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
}
