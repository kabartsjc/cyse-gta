package edu.gmu.cyse.gta.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.gmu.cyse.gta.model.application.GTAApplication;
import edu.gmu.cyse.gta.model.application.GTAHistoryCourse;

public class ApplicationJSON {

	public static GTAApplication parser(String username, String msg) {
		JSONObject json = new JSONObject(msg);
		boolean isInternationalStudent = json.getBoolean("isInternationalStudent");
		boolean wasGTA = json.getBoolean("wasGTACB");

		String appperiod = json.getString("appperiod");

		String appyear = json.getString("appyear");

		String url_video = json.getString("introGTAVideo");

		GTAApplication app = new GTAApplication(username, isInternationalStudent, wasGTA, appyear, appperiod,
				url_video);

		if (wasGTA) {
			JSONArray gtaCourseHistory = json.getJSONArray("gtaCourseHistory");
			List<GTAHistoryCourse> courseHistL = new ArrayList<GTAHistoryCourse>();
			for (int i = 0; i < gtaCourseHistory.length(); i++) {
				JSONObject jsonObject = gtaCourseHistory.getJSONObject(i);
				String cyseId = jsonObject.getString("cyseId");
				String semester = jsonObject.getString("semester");
				String year = jsonObject.getString("year");
				GTAHistoryCourse gtaCourseHist = new GTAHistoryCourse(cyseId, semester, year);
				courseHistL.add(gtaCourseHist);
			}

			app.setGTAHistoryCourses(courseHistL);

		}

		return app;
	}

	public static List<GTAHistoryCourse> parseCourseHistory(String msg) {
		JSONObject json = new JSONObject(msg);
		
		JSONArray gtaCourseHistory = json.getJSONArray("gtaCourseHistory");
		List<GTAHistoryCourse> courseHistL = new ArrayList<GTAHistoryCourse>();
		for (int i = 0; i < gtaCourseHistory.length(); i++) {
			JSONObject jsonObject = gtaCourseHistory.getJSONObject(i);
			String cyseId = jsonObject.getString("cyseId");
			String semester = jsonObject.getString("semester");
			String year = jsonObject.getString("year");
			GTAHistoryCourse gtaCourseHist = new GTAHistoryCourse(cyseId, semester, year);
			courseHistL.add(gtaCourseHist);
		}
		return courseHistL;
	}

}
