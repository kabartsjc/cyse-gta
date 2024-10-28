package edu.gmu.cyse.gta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import edu.gmu.cyse.gta.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	  UserDetails findByLogin(String login);
}
