package edu.gmu.cyse.gta.json;

import org.json.JSONObject;

import edu.gmu.cyse.gta.model.application.GTAApplication;
import edu.gmu.cyse.gta.model.application.GTAApplicationInfo;

public class ApplicationInfoJSON {
	
	public static String build(GTAApplication app, GTAApplicationInfo info) {
		JSONObject result = new JSONObject();
		
		JSONObject appl_info = new JSONObject();
		appl_info.put("username", app.getUsername());
		appl_info.put("isInternationalStudent", app.isInternationalStudent());
		appl_info.put("wasGTA", app.isWasGTA());
		result.put("application_info", appl_info);
		
		JSONObject process_info = new JSONObject();
		process_info.put("ApplicationSubmission", info.getSubmission_time());
		
		process_info.put("CYSEAdminOfficeDecision", info.getCyseAdminOfficeDecision());
		process_info.put("CYSEAdminComments", info.getCyseAdminComments());
		
		process_info.put("CYSEChairDecision", info.getCyseChairDecision());
		process_info.put("CYSEChairComments", info.getCyseChairComments());
		
		process_info.put("CourseAllocated", info.getCourseAllocated());
		process_info.put("ContractSigned", info.isContractSigned());
		
		process_info.put("LastUpdate", info.getLast_update());
		process_info.put("ContractSigned", info.isContractSigned());
		
		process_info.put("CELTD_STATUS", info.getCeltd_file_status());
		process_info.put("CV_STATUS", info.getCv_file_status());
		process_info.put("TRASCRIPT_STATUS", info.getStudent_transcript());
		process_info.put("TOEFL_STATUS", info.getToefl_file_status());
		process_info.put("VIDEO_STATUS", info.getVideo_file_status());
		
		result.put("process_info", process_info);
		
		return result.toString();
	}

}
