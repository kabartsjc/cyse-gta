package edu.gmu.cyse.gta.service;

import edu.gmu.cyse.gta.model.application.GTAApplicationInfo;

import java.util.List;
import java.util.Optional;

public interface GTAApplicationInfoService {

    List<GTAApplicationInfo> getGTAApplicationsnfo();

    Optional<GTAApplicationInfo> getGTAApplicationByUsername(String username);

    boolean hasGTAApplicationInfoWithUsername(String username);

    GTAApplicationInfo createGTAApplicationInfo(GTAApplicationInfo gtaApplication);

    void deleteGTAApplicationInfo(GTAApplicationInfo gtaApplication);
    
    public GTAApplicationInfo updateGTAApplicationInfo(String username, GTAApplicationInfo newValues) ;
}
