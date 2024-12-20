package edu.gmu.cyse.gta.json;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.gmu.cyse.gta.model.application.GTAApplication;
import edu.gmu.cyse.gta.model.application.GTAApplicationInfo;
import edu.gmu.cyse.gta.model.application.GTAHistoryCourse;

public class ApplicationInfoJSON {
	
	public static String build(GTAApplication app, GTAApplicationInfo info) {
		JSONObject result = new JSONObject();
		
		JSONObject appl_info = new JSONObject();
		appl_info.put("username", app.getUsername());
		appl_info.put("isInternationalStudent", app.isInternationalStudent());
		appl_info.put("wasGTA", app.isWasGTA());
		
		JSONArray gta_history = new JSONArray();
		
		List<GTAHistoryCourse>gtaHistoryCourses = app.getGtaHistoryCourses();

        // Loop through the list and create a JSONObject for each entry
        for (GTAHistoryCourse course : gtaHistoryCourses) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cyseId", course.getCyseId());
            jsonObject.put("semester", course.getSemester());
            jsonObject.put("year", course.getYear());

            // Add the JSON object to the JSONArray
            gta_history.put(jsonObject);
        }
        
        appl_info.put("gtaHistoryCourses", gta_history);
        
		
		result.put("application_info", appl_info);
		
		JSONObject process_info = new JSONObject();
		process_info.put("ApplicationSubmission", app.getSubmission_time());
		
		process_info.put("CYSEAdminOfficeDecision", info.getCyseAdminOfficeDecision());
		process_info.put("CYSEAdminComments", info.getCyseAdminComments());
		
		process_info.put("CYSEChairDecision", info.getCyseChairDecision());
		process_info.put("CYSEChairComments", info.getCyseChairComments());
		
		process_info.put("CourseAllocated", info.getCourseAllocated());
		process_info.put("ContractSigned", info.isContractSigned());
		
		process_info.put("LastUpdate", app.getLast_update());
		
		process_info.put("CELTDSTATUS", info.getCeltd_file_status());
		System.out.println(info.getCeltd_file_status());
		
		process_info.put("CVSTATUS", info.getCv_file_status());
		process_info.put("TRASCRIPTSTATUS", info.getStudent_transcript());
		process_info.put("TOEFLSTATUS", info.getToefl_file_status());
		
		process_info.put("VIDEOSTATUS", info.getVideo_file_status());
		        
        
		
		result.put("process_info", process_info);
		
		return result.toString();
	}

}
