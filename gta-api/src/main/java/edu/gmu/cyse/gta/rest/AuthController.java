package edu.gmu.cyse.gta.rest;
import edu.gmu.cyse.gta.exception.DuplicatedUserInfoException;
import edu.gmu.cyse.gta.model.User;
import edu.gmu.cyse.gta.rest.dto.AuthResponse;
import edu.gmu.cyse.gta.rest.dto.LoginRequest;
import edu.gmu.cyse.gta.rest.dto.SignUpRequest;
import edu.gmu.cyse.gta.security.TokenProvider;
import edu.gmu.cyse.gta.security.WebSecurityConfig;
import edu.gmu.cyse.gta.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @PostMapping("/authenticate")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
        return new AuthResponse(token);
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshAccessToken(@RequestParam String refreshToken) {
        try {
            // Generate a new access token using the refresh token
           String newAccessToken = tokenProvider.generateTokenFromRefreshToken(refreshToken);

            // Create a response map
            Map<String, String> response = new HashMap<>();
            response.put("accessToken", newAccessToken);

            // Return the response map with the access token
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(Map.of("error", "Invalid or expired refresh token"));
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public AuthResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userService.hasUserWithUsername(signUpRequest.getUsername())) {
            throw new DuplicatedUserInfoException(String.format("Username %s already been used", signUpRequest.getUsername()));
        }
        if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
            throw new DuplicatedUserInfoException(String.format("Email %s already been used", signUpRequest.getEmail()));
        }

        userService.saveUser(mapSignUpRequestToUser(signUpRequest));

        String token = authenticateAndGetToken(signUpRequest.getUsername(), 
        		signUpRequest.getPassword());
        return new AuthResponse(token);
    }

    private String authenticateAndGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
        		new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generateToken(authentication);
    }

    private User mapSignUpRequestToUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setGmuID(signUpRequest.getGmuID());
        user.setRole(WebSecurityConfig.USER);
        return user;
    }
}
