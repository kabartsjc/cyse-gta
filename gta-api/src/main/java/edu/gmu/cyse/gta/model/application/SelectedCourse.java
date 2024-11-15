package edu.gmu.cyse.gta.model.application;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class SelectedCourse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

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
