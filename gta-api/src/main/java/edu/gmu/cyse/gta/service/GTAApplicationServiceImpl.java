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
	public GTAApplication saveGTAApplication(GTAApplication gtaApplication) {
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

	    // Update fields with the same values from newValues
	    if (newValues.getGtaHistoryCourses().size()>0)
	    	existingApplication.setGTAHistoryCourses(newValues.getGtaHistoryCourses());
	    if (newValues.getSelectedCourse().size()>0)
	    	existingApplication.setSelectedCourse(newValues.getSelectedCourses());
	    if (newValues.getStudentRecords().size()>0)
	    	existingApplication.setStudentRecords(newValues.getStudentRecords());
	    existingApplication.setWasGTA(newValues.isWasGTA());
	    existingApplication.setInternationalStudent(newValues.isInternationalStudent());
	    
	    // Save the updated application
	    return gtaApplicationRepository.save(existingApplication);
	}

}
