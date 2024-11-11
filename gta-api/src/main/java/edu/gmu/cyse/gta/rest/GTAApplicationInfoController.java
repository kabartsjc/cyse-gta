package edu.gmu.cyse.gta.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.gmu.cyse.gta.model.application.GTAApplication;
import edu.gmu.cyse.gta.model.application.GTAApplicationInfo;
import edu.gmu.cyse.gta.service.GTAApplicationInfoServiceImpl;
import edu.gmu.cyse.gta.service.GTAApplicationServiceImpl;
import edu.gmu.cyse.gta.service.UserServiceImpl;


@RestController
@RequestMapping("/app")
public class GTAApplicationInfoController {
	@Autowired
	GTAApplicationServiceImpl gtaApplicationService;

	@Autowired
	GTAApplicationInfoServiceImpl gtaAppInfoService;


	  // Use @RequestParam to receive query parameters
    @GetMapping(value = "/gtainfo", produces = "application/json")
    public String getGTAInfo(@RequestParam String gta_param_name) {
        GTAApplicationInfo info = gtaAppInfoService.getGTAApplicationByUsername(gta_param_name).orElse(null);
        if (info!=null) {
        	GTAApplication app = gtaApplicationService.getGTAApplicationByUsername(gta_param_name).orElse(null);
        	if (app!=null) {
        		
        	}
        }
    	
    	
    	// Handle the request with the 'gta_param_name' query parameter
        return "Received: " + gta_param_name;  // Just a placeholder, replace with actual logic
    }

}
