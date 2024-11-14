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

	private String period;
	private String year;
	
	private String urlVideo;

	private List<GTAHistoryCourse> gtaHistoryCourses;

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

	public void setGTAHistoryCourses(List<GTAHistoryCourse> gtaCourseHistoryList) {
		if (gtaHistoryCourses.size()>0) {
			int index = gtaHistoryCourses.size();
			for (int i=0; i<gtaCourseHistoryList.size();i++) {
				gtaHistoryCourses.add(index+i,gtaCourseHistoryList.get(i));
			}
			
		} else {
			this.gtaHistoryCourses = gtaCourseHistoryList;
		}
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

	public String getPeriod() {
		return period;
	}

	public String getYear() {
		return year;
	}

	public String getURLVideo() {
		return urlVideo;
	}
}
