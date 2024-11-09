package edu.gmu.cyse.gta.model.application;

public class SelectedCourse {
	private String courseID;
	private int order;

	public SelectedCourse(String courseID, int order) {
		super();
		this.courseID = courseID;
		this.order = order;
	}

	public SelectedCourse() {
	}
	
	public String getCourseID() {
		return courseID;
	}

	public int getOrder() {
		return order;
	}



}
