package edu.gmu.cyse.gta.service;

import edu.gmu.cyse.gta.repository.GTAApplicationRepository;
import edu.gmu.cyse.gta.model.application.GTAApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GTAApplicationServiceImpl implements GTAApplicationService {

	private final GTAApplicationRepository gtaApplicationRepository;

	@Override
	public List<GTAApplication> getGTAApplications() {
		return gtaApplicationRepository.findAll();
	}

	@Override
	public Optional<GTAApplication> getGTAApplicationByUsername(String username) {
		return gtaApplicationRepository.findByUsername(username);
	}

	@Override
	public boolean hasGTAApplicationWithUsername(String username) {
		return gtaApplicationRepository.existsByUsername(username);
	}

	@Override
	public GTAApplication createGTAApplication(GTAApplication gtaApplication) {
		gtaApplication.setSubmission_time();
		gtaApplication.setLast_update();
		return gtaApplicationRepository.save(gtaApplication);
	}

	@Override
	public void deleteGTAApplication(GTAApplication gtaApplication) {
		gtaApplicationRepository.delete(gtaApplication);

	}
	
	@Override
	public GTAApplication updateGTAApplicationByUsername(String username, GTAApplication newValues) {
	    // Retrieve the existing application by username
	    GTAApplication existingApplication = gtaApplicationRepository.findByUsername(username)
	        .orElseThrow(() -> new RuntimeException("GTAApplication not found for username: " + username));
	    
	    newValues.setLast_update();
	    newValues.setSubmission_time(existingApplication.getSubmission_time());
	    
	    deleteGTAApplication(existingApplication);
	    
	    // Save the updated application
	    return gtaApplicationRepository.save(newValues);
	}

}
