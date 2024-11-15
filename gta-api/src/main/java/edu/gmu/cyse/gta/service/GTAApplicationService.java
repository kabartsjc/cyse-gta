package edu.gmu.cyse.gta.service;

import edu.gmu.cyse.gta.model.application.GTAApplication;

import java.util.List;
import java.util.Optional;

public interface GTAApplicationService {

    List<GTAApplication> getGTAApplications();

    Optional<GTAApplication> getGTAApplicationByUsername(String username);

    boolean hasGTAApplicationWithUsername(String username);

    GTAApplication createGTAApplication(GTAApplication gtaApplication);

    void deleteGTAApplication(GTAApplication gtaApplication);
    
    public GTAApplication updateGTAApplicationByUsername(String username, GTAApplication newValues) ;
}
