package edu.gmu.cyse.gta.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.gmu.cyse.gta.dtos.SignUpDto;
import edu.gmu.cyse.gta.entities.User;
import edu.gmu.cyse.gta.repositories.UserRepository;


@Service
public class AuthService implements UserDetailsService {

  @Autowired
  UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    var user = repository.findByLogin(username);
    return user;
  }

  public UserDetails signUp(SignUpDto data) throws Exception {
    if (repository.findByLogin(data.login()) != null) {
      throw new Exception("Username already exists");
    }
    String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
    User newUser = new User(data.login(), encryptedPassword, data.role());
    return repository.save(newUser);
  }
}
