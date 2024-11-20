package edu.gmu.cyse.gta.service;

import edu.gmu.cyse.gta.repository.GTAApplicationRepository;
import edu.gmu.cyse.gta.model.application.GTAApplication;
import edu.gmu.cyse.gta.model.application.GTAHistoryCourse;
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
	public GTAApplication updateGTAApplicationGTA(String username, GTAApplication newAppl) {
	    // Retrieve the existing application by username
	    GTAApplication existingApplication = gtaApplicationRepository.findByUsername(username)
	        .orElseThrow(() -> new RuntimeException("GTAApplication not found for username: " + username));
	    
	    newAppl.setLast_update();
	    newAppl.setSubmission_time(existingApplication.getSubmission_time());
	    
	    String url_video=null;
	    
	    if (existingApplication.getUrlVideo()!=null)
	    	url_video=existingApplication.getUrlVideo();
	    if (newAppl.getUrlVideo()!=null)
	    	url_video=newAppl.getUrlVideo();
	    
	    newAppl.setURLVIdeo(url_video);
	    
	    
	    deleteGTAApplication(existingApplication);
	    
	    // Save the updated application
	    return gtaApplicationRepository.save(newAppl);
	}
	
	
	@Override
	public GTAApplication updateGTAApplication(String username, GTAApplication newAppl) {
	    // Retrieve the existing application by username
	    GTAApplication existingApplication = gtaApplicationRepository.findByUsername(username)
	        .orElseThrow(() -> new RuntimeException("GTAApplication not found for username: " + username));
	    
	    
	    List<GTAHistoryCourse>courseL = existingApplication.getGtaHistoryCourses();
	    
	    String url_video=null;
	    
	    if (existingApplication.getUrlVideo()!=null) {
	    	url_video=existingApplication.getUrlVideo();
	    }
	    if (newAppl.getUrlVideo()!=null) {
	    	url_video=newAppl.getUrlVideo();
	    }
	    
	    newAppl.setURLVIdeo(url_video);
	    
	    newAppl.setLast_update();
	    newAppl.setSubmission_time(existingApplication.getSubmission_time());
	    newAppl.setGTAHistoryCourses(courseL);
	    if (newAppl.getGtaHistoryCourses().size()>0)
	    	newAppl.setWasGTA(true);
	    
	    deleteGTAApplication(existingApplication);
	    
	    // Save the updated application
	    return gtaApplicationRepository.save(newAppl);
	}

}
