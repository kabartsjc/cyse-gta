package edu.gmu.cyse.gta.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.gmu.cyse.gta.model.application.GTAApplication;
import edu.gmu.cyse.gta.model.application.GTAHistoryCourse;
import edu.gmu.cyse.gta.model.application.SelectedCourse;
import edu.gmu.cyse.gta.model.application.StudentRecord;

public class ApplicationJSONParser {
	
	public static GTAApplication parserApplication(String username, String msg) {
		JSONObject json = new JSONObject(msg);
		boolean isInternationalStudent = json.getBoolean("isInternationalStudent");
		boolean wasGTA = json.getBoolean("wasGTACB");
		boolean isCYSEStudent= json.getBoolean("isCYSEStudent");
		
		
		GTAApplication app = new GTAApplication(username,isCYSEStudent,isInternationalStudent, wasGTA);
		
		if (wasGTA) {
			JSONArray gtaCourseHistory = json.getJSONArray("gtaCourseHistory");
			List<GTAHistoryCourse> courseHistL = new ArrayList<GTAHistoryCourse>();
			for (int i=0; i<gtaCourseHistory.length();i++) {
				JSONObject jsonObject = gtaCourseHistory.getJSONObject(i);
	            String cyseId = jsonObject.getString("cyseId");
	            String semester = jsonObject.getString("semester");
	            String year = jsonObject.getString("year");
	            GTAHistoryCourse gtaCourseHist = new GTAHistoryCourse(cyseId, semester, year);
	            courseHistL.add(gtaCourseHist);
			}
			
			app.setGTAHistoryCourses(courseHistL);
			
		}
		
		if (isCYSEStudent) {
			JSONArray studentHistory = json.getJSONArray("studentHistory");
			List<StudentRecord> studentHistL = new ArrayList<StudentRecord>();
			for (int i=0; i<studentHistory.length();i++) {
				JSONObject jsonObject = studentHistory.getJSONObject(i);
	            String cyseId = jsonObject.getString("cyseId");
	            String semester = jsonObject.getString("semester");
	            String year = jsonObject.getString("year");
	            String grade = jsonObject.getString("grade");
	            StudentRecord stHis = new StudentRecord(cyseId, semester, year, grade);
	            studentHistL.add(stHis);
			}
			app.setStudentRecords(studentHistL);	

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

		}
				
		

		return app;
	}

}
