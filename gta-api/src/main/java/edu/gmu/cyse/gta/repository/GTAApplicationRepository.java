package edu.gmu.cyse.gta.repository;

import edu.gmu.cyse.gta.model.application.GTAApplication;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GTAApplicationRepository extends MongoRepository<GTAApplication, Long> {
	Optional<GTAApplication> findByUsername(String username);

    boolean existsByUsername(String username);


    
}
