package edu.gmu.cyse.gta.model.application;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.gmu.cyse.gta.rest.ApplicationController;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class GTAApplicationInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	private String username;

	private enum DECISION_STATUS {
		inprocessing, approved, deny, incompleted, notstarted
	}
	
	private enum FILE_STATUS {
		nosubmited,inprocessing, checked, error
	}

	private DECISION_STATUS cyseAdminOfficeDecision = DECISION_STATUS.notstarted;
	private String cyseAdminComments = "";

	private DECISION_STATUS cyseChairDecision = DECISION_STATUS.notstarted;
	private String cyseChairComments = "";

	private String courseAllocated;
	private boolean contractSigned = false;
	
	private FILE_STATUS cv_file_status = FILE_STATUS.nosubmited;
	private FILE_STATUS video_file_status= FILE_STATUS.nosubmited;
	private FILE_STATUS celtd_file_status= FILE_STATUS.nosubmited;
	private FILE_STATUS toefl_file_status= FILE_STATUS.nosubmited;
	private FILE_STATUS student_transcript= FILE_STATUS.nosubmited;

	
	public GTAApplicationInfo(String username) {
		this.username = username;
		
	}
	
	
	public void update(GTAApplication app) {
		if (app.getURLVideo()!=null && video_file_status== FILE_STATUS.nosubmited)
			video_file_status= FILE_STATUS.inprocessing;
		
		String username = app.getUsername();
		String userFolderPath = ApplicationController.BASE_DIR + File.separator + username;
		
		String cvPath = userFolderPath + File.separator + "cv.pdf";
		Path path = Paths.get(cvPath);
		if (Files.exists(path) && cv_file_status== FILE_STATUS.nosubmited) {
            cv_file_status=FILE_STATUS.inprocessing;
        } else if (Files.exists(path) ==false) {
        	cv_file_status=FILE_STATUS.nosubmited;
        }
		
		
		String celtdPath = userFolderPath + File.separator + "celtd.pdf";
		path = Paths.get(celtdPath);
		if (Files.exists(path)&& celtd_file_status== FILE_STATUS.nosubmited) {
            celtd_file_status=FILE_STATUS.inprocessing;
        } else if (Files.exists(path) ==false)
        	celtd_file_status=FILE_STATUS.nosubmited;
		
		
		String toelfPath = userFolderPath + File.separator + "toelf.pdf";
		path = Paths.get(toelfPath);
		if (Files.exists(path) && toefl_file_status== FILE_STATUS.nosubmited) {
            toefl_file_status=FILE_STATUS.inprocessing;
        } else if (Files.exists(path) ==false)
        	toefl_file_status=FILE_STATUS.nosubmited;
		
		
		String transcPath = userFolderPath + File.separator + "transcript.pdf";
		path = Paths.get(transcPath);
		if (Files.exists(path)&& student_transcript== FILE_STATUS.nosubmited) {
            student_transcript=FILE_STATUS.inprocessing;
        } else if (Files.exists(path) ==false)
        	student_transcript=FILE_STATUS.nosubmited;
	}

	public GTAApplicationInfo() {}
	
	
	
	public String getUsername() {
		return username;
	}
	
	
	public DECISION_STATUS getCyseAdminOfficeDecision() {
		return cyseAdminOfficeDecision;
	}

	public String getCyseAdminComments() {
		return cyseAdminComments;
	}

	public DECISION_STATUS getCyseChairDecision() {
		return cyseChairDecision;
	}

	public String getCyseChairComments() {
		return cyseChairComments;
	}

	public String getCourseAllocated() {
		return courseAllocated;
	}

	public boolean isContractSigned() {
		return contractSigned;
	}


	public FILE_STATUS getCv_file_status() {
		return cv_file_status;
	}

	public void setCv_file_status(FILE_STATUS cv_file_status) {
		this.cv_file_status = cv_file_status;
	}

	public FILE_STATUS getVideo_file_status() {
		return video_file_status;
	}

	public void setVideo_file_status(FILE_STATUS video_file_status) {
		this.video_file_status = video_file_status;
	}

	public FILE_STATUS getCeltd_file_status() {
		return celtd_file_status;
	}

	public void setCeltd_file_status(FILE_STATUS celtd_file_status) {
		this.celtd_file_status = celtd_file_status;
	}

	public FILE_STATUS getToefl_file_status() {
		return toefl_file_status;
	}

	public void setToefl_file_status(FILE_STATUS toefl_fole_status) {
		this.toefl_file_status = toefl_fole_status;
	}

	public FILE_STATUS getStudent_transcript() {
		return student_transcript;
	}

	public void setStudent_transcript(FILE_STATUS student_transcript) {
		this.student_transcript = student_transcript;
	}
	
	

}
