package edu.gmu.cyse.gta.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.gmu.cyse.gta.model.application.Application;
import edu.gmu.cyse.gta.model.application.GTACourseHistory;
import edu.gmu.cyse.gta.model.application.SelectedCourse;
import edu.gmu.cyse.gta.model.application.StudentHistory;

public class ApplicationJSONParser {
	
	public static Application parserApplication(String username, String msg) {
		JSONObject json = new JSONObject(msg);
		boolean isInternationalStudent = json.getBoolean("isInternationalStudent");
		boolean wasGTA = json.getBoolean("wasGTACB");
		
		Application app = new Application(username,isInternationalStudent, wasGTA);
		
		if (wasGTA) {
			JSONArray gtaCourseHistory = json.getJSONArray("gtaCourseHistory");
			List<GTACourseHistory> courseHistL = new ArrayList<GTACourseHistory>();
			for (int i=0; i<gtaCourseHistory.length();i++) {
				JSONObject jsonObject = gtaCourseHistory.getJSONObject(i);
	            String cyseId = jsonObject.getString("cyseId");
	            String semester = jsonObject.getString("semester");
	            String year = jsonObject.getString("year");
	            GTACourseHistory gtaCourseHist = new GTACourseHistory(cyseId, semester, year);
	            courseHistL.add(gtaCourseHist);
			}
			
			app.setGtaCourseHistoryList(courseHistL);
			
		}
				
		JSONArray studentHistory = json.getJSONArray("studentHistory");
		List<StudentHistory> studentHistL = new ArrayList<StudentHistory>();
		for (int i=0; i<studentHistory.length();i++) {
			JSONObject jsonObject = studentHistory.getJSONObject(i);
            String cyseId = jsonObject.getString("cyseId");
            String semester = jsonObject.getString("semester");
            String year = jsonObject.getString("year");
            String grade = jsonObject.getString("grade");
            StudentHistory stHis = new StudentHistory(cyseId, semester, year, grade);
            studentHistL.add(stHis);
		}
		app.setStudentHistoryList(studentHistL);

		JSONArray selectedCourses = json.getJSONArray("selectedCourses");
		List<SelectedCourse>selCourseList = new ArrayList<SelectedCourse>();
		for (int i=0; i<selectedCourses.length();i++) {
			JSONObject jsonObject = selectedCourses.getJSONObject(i);
            String cyseId = jsonObject.getString("cyseId");
            int order = jsonObject.getInt("order");
            SelectedCourse selCourse = new SelectedCourse(cyseId, order);
            selCourseList.add(selCourse);
		}
		app.setSelectedCourse(selCourseList);

		return app;
	}

}
