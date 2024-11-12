package edu.gmu.cyse.gta.model.application;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class GTAApplication {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	private String username;

	private boolean isInternationalStudent = false;
	private boolean wasGTA = false;
	
	private List<GTAHistoryCourse> gtaHistoryCourses;

	public GTAApplication() {
		this.gtaHistoryCourses = new ArrayList<GTAHistoryCourse>();
	}

	public GTAApplication(String username, boolean isInternationalStudent, boolean wasGTA) {
		super();
		this.username = username;
		this.isInternationalStudent = isInternationalStudent;
		this.wasGTA = wasGTA;

		this.gtaHistoryCourses = new ArrayList<GTAHistoryCourse>();
	}

	public void setGTAHistoryCourses(List<GTAHistoryCourse> gtaCourseHistoryList) {
		this.gtaHistoryCourses = gtaCourseHistoryList;
	}


	public boolean isInternationalStudent() {
		return isInternationalStudent;
	}

	public boolean isWasGTA() {
		return wasGTA;
	}
	
	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public List<GTAHistoryCourse> getGtaHistoryCourses() {
		return gtaHistoryCourses;
	}

	public void setWasGTA(boolean wasGTA) {
		this.wasGTA = wasGTA;
	}

	public void setInternationalStudent(boolean internationalStudent) {
		this.isInternationalStudent = internationalStudent;
	}


}