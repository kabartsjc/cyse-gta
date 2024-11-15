package edu.gmu.cyse.gta.model.application;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class StudentRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	private String cyseId;
	private String semester;
	private String year;
	private String grade;
	
	public StudentRecord(String cyseId, String semester, String year, String grade) {
		super();
		this.cyseId = cyseId;
		this.semester = semester;
		this.year = year;
		this.grade=grade;
	}
	
	public StudentRecord() {
		
	}

	public String getCyseId() {
		return cyseId;
	}

	public String getSemester() {
		return semester;
	}

	public String getYear() {
		return year;
	}

	public String getGrade() {
		return grade;
	}
	
	

}
