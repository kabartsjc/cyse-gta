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

	private DECISION_STATUS cyseAdminOfficeDecision = DECISION_STATUS.notstarted;
	private String cyseAdminComments = "";

	private DECISION_STATUS cyseChairDecision = DECISION_STATUS.notstarted;
	private String cyseChairComments = "";

	private String courseAllocated;
	private boolean contractSigned = false;

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

}
