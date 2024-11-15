package edu.gmu.cyse.gta.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.gmu.cyse.gta.json.ApplicationInfoJSON;
import edu.gmu.cyse.gta.model.application.GTAApplication;
import edu.gmu.cyse.gta.model.application.GTAApplicationInfo;
import edu.gmu.cyse.gta.service.GTAApplicationInfoServiceImpl;
import edu.gmu.cyse.gta.service.GTAApplicationServiceImpl;


@RestController
@RequestMapping("/app")
public class GTAInfoController {
	@Autowired
	GTAApplicationServiceImpl gtaApplicationService;

	@Autowired
	GTAApplicationInfoServiceImpl gtaAppInfoService;


	// Use @RequestParam to receive query parameters
    @GetMapping(value = "/gtainfo", produces = "application/json")
    public String getGTAInfo(@RequestParam String gta_param_name) {
        String answer=null;
    	GTAApplicationInfo info = gtaAppInfoService.getGTAApplicationByUsername(gta_param_name).orElse(null);
        if (info!=null) {
        	GTAApplication app = gtaApplicationService.getGTAApplicationByUsername(gta_param_name).orElse(null);
        	if (app!=null) {
        		answer= ApplicationInfoJSON.build(app, info);
        	}
        	else 
        		answer="no data about the application";
        }
        else         		
        	answer="no data about the application";
        return answer;  // Just a placeholder, replace with actual logic
    }

}
