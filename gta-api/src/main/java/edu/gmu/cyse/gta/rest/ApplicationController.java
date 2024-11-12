package edu.gmu.cyse.gta.rest;

import static edu.gmu.cyse.gta.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.gmu.cyse.gta.exception.InvalidFileTypeException;
import edu.gmu.cyse.gta.json.ApplicationJSON;
import edu.gmu.cyse.gta.model.User;
import edu.gmu.cyse.gta.model.application.GTAApplication;
import edu.gmu.cyse.gta.model.application.GTAApplicationInfo;
import edu.gmu.cyse.gta.security.CustomUserDetails;
import edu.gmu.cyse.gta.service.GTAApplicationInfoServiceImpl;
import edu.gmu.cyse.gta.service.GTAApplicationServiceImpl;
import edu.gmu.cyse.gta.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/gta")
public class ApplicationController {
	@Autowired
	UserServiceImpl userService;

	@Autowired
	GTAApplicationInfoServiceImpl gtaAppInfoService;

	@Autowired
	GTAApplicationServiceImpl gtaApplicationService;

	public static final String BASE_DIR = "/home/kabart/gta_files";

	@PostMapping(value = "/application", consumes = "multipart/form-data")
	@Operation(security = { @SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME) })
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> createApplication(@AuthenticationPrincipal CustomUserDetails currentUser,
			@RequestParam(value = "cvFile", required = false) MultipartFile cvFile, 
			@RequestParam("application_data") String applicationDataJson, // Handle application data as JSON string
			@RequestParam(value = "celtdCertFile", required = false) MultipartFile celtdCertFile,
			@RequestParam(value = "toeflScoreFile", required = false) MultipartFile toeflScoreFile,
			@RequestParam(value = "transcriptFile", required = false) MultipartFile transcriptFile,
			@RequestParam("username") String username) {

		try {
			boolean application_exists = false;
			GTAApplication application = null;
			GTAApplicationInfo gtaAppInfo = null;
			boolean error = false;
			String error_msg = "Your application is incompleted. It contains these errors:\n";

			User user = userService.getUserByUsername(username).orElse(null);
			if (user == null)
				throw new InvalidFileTypeException("An unexpected error occurred.");

			if (username != null) {
				application_exists = gtaApplicationService.hasGTAApplicationWithUsername(username);
				if (application_exists) {
					application = gtaApplicationService.getGTAApplicationByUsername(username).orElse(null);
					GTAApplication newapplication = ApplicationJSON.parser(username, applicationDataJson);
					gtaApplicationService.updateGTAApplicationByUsername(username, newapplication);
					
					gtaAppInfo = gtaAppInfoService.getGTAApplicationByUsername(username).orElse(null);

				}

				else if (applicationDataJson != null && user != null) {
					application = ApplicationJSON.parser(username, applicationDataJson);
					gtaApplicationService.saveGTAApplication(application);
					user.setHasApplication(true);

					gtaAppInfo = new GTAApplicationInfo(username);
					gtaAppInfoService.saveGTAApplicationInfo(gtaAppInfo);

					userService.saveUser(user);

				} else {
					throw new InvalidFileTypeException("An unexpected error occurred.");
				}

				String userFolderPath = BASE_DIR + File.separator + username;

				File userFolder = new File(userFolderPath);
				if (!userFolder.exists()) {
					userFolder.mkdirs(); // Create the directory if it does not exist
				}

				if (cvFile != null) {
					String contentType = cvFile.getContentType();
					if (contentType != null && !contentType.equals("application/pdf")) {
						error = true;
						error_msg = error_msg + "- Uploaded cv file is not a PDF!\n";
					} else {
						String filePath = userFolderPath + File.separator + "cv.pdf";
						Files.copy(cvFile.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
					}
				}

				
				if (application.isInternationalStudent()) {
					if (celtdCertFile != null) {
						String contentType = celtdCertFile.getContentType();
						if (contentType != null && !contentType.equals("application/pdf")) {
							error = true;
							error_msg = error_msg + "- Uploaded CELTD file is not a PDF!\n";
						} else {
							String filePath = userFolderPath + File.separator + "celtd.pdf";
							Files.copy(celtdCertFile.getInputStream(), Paths.get(filePath),
									StandardCopyOption.REPLACE_EXISTING);
						}
					}

					if (toeflScoreFile != null) {
						String contentType = toeflScoreFile.getContentType();
						if (contentType != null && !contentType.equals("application/pdf")) {
							error = true;
							error_msg = error_msg + "- Uploaded TOELF file is not a PDF!\n";
						} else {
							String filePath = userFolderPath + File.separator + "toelf.pdf";
							Files.copy(toeflScoreFile.getInputStream(), Paths.get(filePath),
									StandardCopyOption.REPLACE_EXISTING);
						}

					}

				}

				if (transcriptFile != null) {
					String contentType = transcriptFile.getContentType();
					if (contentType != null && !contentType.equals("application/pdf")) {
						error = true;
						error_msg = error_msg + "- Uploaded NO CYSE transcript file is not a PDF!\n";
					} else {
						String filePath = userFolderPath + File.separator + "transcript.pdf";
						Files.copy(transcriptFile.getInputStream(), Paths.get(filePath),
								StandardCopyOption.REPLACE_EXISTING);
					}
				}
				gtaAppInfo.update(application);
				
				gtaAppInfoService.saveGTAApplicationInfo(gtaAppInfo);

			} else
				throw new InvalidFileTypeException(
						"Application was sent without username, clear the cache and try again!");

			if (error)
				throw new InvalidFileTypeException(error_msg);
			else
				return ResponseEntity.ok("Application submitted successfully");

		} catch (InvalidFileTypeException e) {
			String errorMessage = e.getMessage();
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		} catch (Exception e) {
			String errorMessage = "An unexpected error occurred.";

			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
