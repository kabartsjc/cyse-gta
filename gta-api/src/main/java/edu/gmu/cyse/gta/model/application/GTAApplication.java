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
	
	private boolean isCYSEStudent=true;

	private List<GTAHistoryCourse> gtaHistoryCourses;

	private List<StudentRecord> studentRecords;

	private List<SelectedCourse> selectedCourses;

	public GTAApplication() {
		this.gtaHistoryCourses = new ArrayList<GTAHistoryCourse>();
		this.studentRecords = new ArrayList<StudentRecord>();
		this.selectedCourses = new ArrayList<SelectedCourse>();

	}

	public GTAApplication(String username, boolean isCYSEStudent, boolean isInternationalStudent, boolean wasGTA) {
		super();
		this.username = username;
		this.isInternationalStudent = isInternationalStudent;
		this.wasGTA = wasGTA;
		this.isCYSEStudent=isCYSEStudent;

		this.gtaHistoryCourses = new ArrayList<GTAHistoryCourse>();
		this.studentRecords = new ArrayList<StudentRecord>();
		this.selectedCourses = new ArrayList<SelectedCourse>();
	}

	public void setGTAHistoryCourses(List<GTAHistoryCourse> gtaCourseHistoryList) {
		this.gtaHistoryCourses = gtaCourseHistoryList;
	}

	public List<StudentRecord> getStudentRecords() {
		return studentRecords;
	}

	public void setStudentRecords(List<StudentRecord> studentRecords) {
		this.studentRecords = studentRecords;
	}

	public List<SelectedCourse> getSelectedCourse() {
		return selectedCourses;
	}

	public void setSelectedCourse(List<SelectedCourse> selectedCourses) {
		this.selectedCourses = selectedCourses;
	}

	public boolean isInternationalStudent() {
		return isInternationalStudent;
	}

	public boolean isWasGTA() {
		return wasGTA;
	}

	public List<SelectedCourse> getSelectedCourses() {
		return selectedCourses;
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

	public boolean isCYSEStudent() {
		return isCYSEStudent;
	}

	public void setCYSEStudent(boolean isCYSEStudent) {
		this.isCYSEStudent = isCYSEStudent;
	}
	
	
	

}
