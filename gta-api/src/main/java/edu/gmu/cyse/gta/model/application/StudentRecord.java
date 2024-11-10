package edu.gmu.cyse.gta.model.application;

public class StudentHistory {
	private String cyseId;
	private String semester;
	private String year;
	private String grade;

	public StudentHistory(String cyseId, String semester, String year, String grade) {
		super();
		this.cyseId = cyseId;
		this.semester = semester;
		this.year = year;
		this.grade=grade;
	}
	
	public StudentHistory() {
		
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
