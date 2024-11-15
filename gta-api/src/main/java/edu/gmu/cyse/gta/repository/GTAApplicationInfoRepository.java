package edu.gmu.cyse.gta.repository;

import edu.gmu.cyse.gta.model.application.GTAApplicationInfo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GTAApplicationInfoRepository extends MongoRepository<GTAApplicationInfo, Long> {
	Optional<GTAApplicationInfo> findByUsername(String username);

    boolean existsByUsername(String username);


    
}
