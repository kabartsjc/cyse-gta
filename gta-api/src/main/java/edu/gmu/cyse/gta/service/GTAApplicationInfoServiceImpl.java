package edu.gmu.cyse.gta.service;

import edu.gmu.cyse.gta.repository.GTAApplicationInfoRepository;
import edu.gmu.cyse.gta.model.application.GTAApplicationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GTAApplicationInfoServiceImpl implements GTAApplicationInfoService {

	private final GTAApplicationInfoRepository gtaApplicationInfoRepository;

	@Override
	public List<GTAApplicationInfo> getGTAApplicationsnfo() {
		return gtaApplicationInfoRepository.findAll();
	}

	@Override
	public Optional<GTAApplicationInfo> getGTAApplicationByUsername(String username) {
		return gtaApplicationInfoRepository.findByUsername(username);
	}

	@Override
	public boolean hasGTAApplicationInfoWithUsername(String username) {
		return gtaApplicationInfoRepository.existsByUsername(username);
	}

	@Override
	public GTAApplicationInfo saveGTAApplicationInfo(GTAApplicationInfo gtaApplication) {
		return gtaApplicationInfoRepository.save(gtaApplication);
	}

	@Override
	public void deleteGTAApplicationInfo(GTAApplicationInfo gtaApplication) {
		gtaApplicationInfoRepository.delete(gtaApplication);
	}

	@Override
	public GTAApplicationInfo updateGTAApplicationInfoByUsername(String username, GTAApplicationInfo newValues) {
		// Retrieve the existing application by username
		GTAApplicationInfo existingApplication = gtaApplicationInfoRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("GTAApplication not found for username: " + username));

		deleteGTAApplicationInfo(existingApplication);

		// Save the updated application
		return gtaApplicationInfoRepository.save(newValues);

	}

}
