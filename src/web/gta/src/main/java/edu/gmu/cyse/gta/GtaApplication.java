package edu.gmu.cyse.gta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "edu.gmu.cyse.gta.repositories")
@EntityScan(basePackages = "edu.gmu.cyse.gta.entities")

@PropertySource("file:${user.dir}/.env")
public class GtaApplication {
	public static void main(String[] args) {
		SpringApplication.run(GtaApplication.class, args);
	}

}
