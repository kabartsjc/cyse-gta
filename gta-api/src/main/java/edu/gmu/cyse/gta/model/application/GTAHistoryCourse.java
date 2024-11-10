package edu.gmu.cyse.gta.model.application;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class GTAHistoryCourse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	private String cyseId;
	private String semester;
	private String year;

	public GTAHistoryCourse(String cyseId, String semester, String year) {
		super();
		this.cyseId = cyseId;
		this.semester = semester;
		this.year = year;
	}

	public GTAHistoryCourse() {

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
