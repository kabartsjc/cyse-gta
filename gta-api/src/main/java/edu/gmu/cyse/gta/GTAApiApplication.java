package edu.gmu.cyse.gta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "edu.gmu.cyse.gta.repository")
@EntityScan(basePackages = "edu.gmu.cyse.gta.model")


public class GTAApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GTAApiApplication.class, args);
    }
}
