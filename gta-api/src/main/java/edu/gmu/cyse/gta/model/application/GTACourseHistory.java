package edu.gmu.cyse.gta.model.application;

public class GTACourseHistory {
	private String cyseId;
	private String semester;
	private String year;

	public GTACourseHistory(String cyseId, String semester, String year) {
		super();
		this.cyseId = cyseId;
		this.semester = semester;
		this.year = year;
	}
	
	public GTACourseHistory() {
		
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

}
