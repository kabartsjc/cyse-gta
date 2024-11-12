package edu.gmu.cyse.gta.model.application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
	
	private FILE_STATUS cv_file_status = FILE_STATUS.inprocessing;
	private FILE_STATUS video_file_status= FILE_STATUS.inprocessing;
	private FILE_STATUS celtd_file_status= FILE_STATUS.inprocessing;
	private FILE_STATUS toefl_file_status= FILE_STATUS.inprocessing;
	private FILE_STATUS student_transcript= FILE_STATUS.inprocessing;

	private String submission_time;
	private String last_update;
	private String last_user_update;
	
	public GTAApplicationInfo(String username) {
		this.username = username;
		submission_time = getTime();
		last_update = submission_time;
	}

	public GTAApplicationInfo() {}
	
	public String getTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
	
	
	public String getUsername() {
		return username;
	}
	
	public void setCyseAdminOfficeDecision(String user, DECISION_STATUS cyseAdminOfficeDecision) {
		this.cyseAdminOfficeDecision = cyseAdminOfficeDecision;
		last_update=getTime();
		last_user_update=user;
	}

	public void setCyseAdminComments(String user, String cyseAdminComments) {
		this.cyseAdminComments = cyseAdminComments;
		last_update=getTime();
		last_user_update=user;
	}

	public void setCyseChairDecision(String user,DECISION_STATUS cyseChairDecision) {
		this.cyseChairDecision = cyseChairDecision;
		last_update=getTime();
		last_user_update=user;

	}

	public void setCyseChairComments(String user, String cyseChairComments) {
		this.cyseChairComments = cyseChairComments;
		last_update=getTime();
		last_user_update=user;
	}

	public void setCourseAllocated(String user, String courseAllocated) {
		this.courseAllocated = courseAllocated;
		last_update=getTime();
		last_user_update=user;
	}

	public void setContractSigned(String user, boolean contractSigned) {
		this.contractSigned = contractSigned;
		last_update=getTime();
		last_user_update=user;
	}

	public void setLast_update(String last_update) {
		this.last_update = last_update;
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

	public String getSubmission_time() {
		return submission_time;
	}

	public String getLast_update() {
		return last_update;
	}

	public String getLast_user_update() {
		return last_user_update;
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
