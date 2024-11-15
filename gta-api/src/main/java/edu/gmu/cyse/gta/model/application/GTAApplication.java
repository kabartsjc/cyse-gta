package edu.gmu.cyse.gta.model.application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

	private String period;
	private String year;
	
	private String urlVideo;

	private List<GTAHistoryCourse> gtaHistoryCourses;
	
	private String submission_time;
	private String last_update;
	

	public GTAApplication() {
		this.gtaHistoryCourses = new ArrayList<GTAHistoryCourse>();
	}

	public GTAApplication(String username, boolean isInternationalStudent, boolean wasGTA, 
			String year, String period, String url_video) {
		super();
		this.username = username;
		this.isInternationalStudent = isInternationalStudent;
		this.wasGTA = wasGTA;
		
		this.urlVideo=url_video;

		this.year = year;
		this.period = period;

		this.gtaHistoryCourses = new ArrayList<GTAHistoryCourse>();
	}
	
	public String getTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
	

	public void setGTAHistoryCourses(List<GTAHistoryCourse> gtaCourseHistoryList) {
		this.gtaHistoryCourses = gtaCourseHistoryList;
		last_update=getTime();
		
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
		last_update=getTime();
	}

	public void setInternationalStudent(boolean internationalStudent) {
		this.isInternationalStudent = internationalStudent;
		last_update=getTime();
	}

	public String getPeriod() {
		return period;
	}

	public String getYear() {
		return year;
	}

	public String getURLVideo() {
		return urlVideo;
	}

	public String getUrlVideo() {
		return urlVideo;
	}

	public String getSubmission_time() {
		return submission_time;
	}

	public String getLast_update() {
		return last_update;
	}

	public void setSubmission_time() {
		this.submission_time = getTime();
	}
	
	public void setSubmission_time(String sub_time) {
		this.submission_time = sub_time;
	}
	
	
	public void setLast_update() {
		this.last_update= getTime();
	}
	
}
