package edu.gmu.cyse.gta.model.application;

import java.util.ArrayList;
import java.util.List;

public class Application {
	
	private String username;

	private boolean isInternationalStudent = false;
	private boolean wasGTA = false;

	private List<GTACourseHistory> gtaCourseHistoryList;

	private List<StudentHistory> studentHistoryList;

	private List<SelectedCourse> selectedCourses;

	public Application() {
		this.gtaCourseHistoryList=new ArrayList<GTACourseHistory>();
		this.studentHistoryList=new ArrayList<StudentHistory>();
		this.selectedCourses = new ArrayList<SelectedCourse>();

	}

	public Application(String username, boolean isInternationalStudent, boolean wasGTA) {
		super();
		this.username=username;
		this.isInternationalStudent = isInternationalStudent;
		this.wasGTA = wasGTA;

		this.gtaCourseHistoryList=new ArrayList<GTACourseHistory>();
		this.studentHistoryList=new ArrayList<StudentHistory>();
		this.selectedCourses = new ArrayList<SelectedCourse>();
	}

	public List<GTACourseHistory> getGtaCourseHistoryList() {
		return gtaCourseHistoryList;
	}

	public void setGtaCourseHistoryList(List<GTACourseHistory> gtaCourseHistoryList) {
		this.gtaCourseHistoryList = gtaCourseHistoryList;
	}

	public List<StudentHistory> getStudentHistoryList() {
		return studentHistoryList;
	}

	public void setStudentHistoryList(List<StudentHistory> studentHistoryList) {
		this.studentHistoryList = studentHistoryList;
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
	
	public String getUsername() {
		return username;
	}

	
}
