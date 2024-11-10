package edu.gmu.cyse.gta.runner;

import edu.gmu.cyse.gta.model.User;
import edu.gmu.cyse.gta.security.WebSecurityConfig;
import edu.gmu.cyse.gta.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userService.getUsers().isEmpty()) {
            return;
        }
        USERS.forEach(user -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
        });
        log.info("Database initialized");
    }

    private static final List<User> USERS = Arrays.asList(
            new User("admin", "admin", "Admin", "admin@mycompany.com", "G27727272", WebSecurityConfig.ADMIN),
            new User("kabart", "1234", "Alexandre Barreto", "kabart@mycompany.com", "G27727221", WebSecurityConfig.USER)
            
    );
}
