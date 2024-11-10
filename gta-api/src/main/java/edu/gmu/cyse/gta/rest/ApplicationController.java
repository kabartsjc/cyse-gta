package edu.gmu.cyse.gta.rest;

import static edu.gmu.cyse.gta.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.gmu.cyse.gta.exception.InvalidFileTypeException;
import edu.gmu.cyse.gta.json.ApplicationJSONParser;
import edu.gmu.cyse.gta.model.application.Application;
import edu.gmu.cyse.gta.rest.dto.ApplicationDto;
import edu.gmu.cyse.gta.rest.dto.CreateApplicationRequest;
import edu.gmu.cyse.gta.security.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/gta")
public class ApplicationController {

	@PostMapping(value = "/application", consumes = "multipart/form-data")
	@Operation(security = { @SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME) })
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> createApplication(@AuthenticationPrincipal CustomUserDetails currentUser,
			@RequestParam("cvFile") MultipartFile cvFile, @RequestParam("introGTAVideo") MultipartFile introGTAVideo,
			@RequestParam("application_data") String applicationDataJson, // Handle application data as JSON string
			@RequestParam(value = "celtdCertFile", required = false) MultipartFile celtdCertFile,
			@RequestParam(value = "toeflScoreFile", required = false) MultipartFile toeflScoreFile,
			 @RequestParam("username") String username) {
		String errorMessage = "An unexpected error occurred.";

		try {
			Application application = null;

			if (applicationDataJson != null) {
				application = ApplicationJSONParser.parserApplication(username, applicationDataJson);
			} else {
				throw new InvalidFileTypeException("An unexpected error occurred.");
			}

			if (cvFile != null) {
				String contentType = cvFile.getContentType();
				if (contentType != null && !contentType.equals("application/pdf")) {
					throw new InvalidFileTypeException("Uploaded cv file is not a PDF!");
				}
			}

			if (introGTAVideo != null) {
				String fileName = introGTAVideo.getOriginalFilename();
				String fileExtension = fileName != null ? fileName.substring(fileName.lastIndexOf(".") + 1) : "";
				if (fileExtension.equalsIgnoreCase("mp4") || fileExtension.equalsIgnoreCase("avi")
						|| fileExtension.equalsIgnoreCase("mpeg")) {

				} else {
					throw new InvalidFileTypeException("Uploaded MP4, MPEG or AVI file to your video!");
				}

			}
			
			if (application.isInternationalStudent()) {
				if (celtdCertFile != null) {
					String contentType = celtdCertFile.getContentType();
					if (contentType != null && !contentType.equals("application/pdf")) {
						throw new InvalidFileTypeException("Uploaded celtd file is not a PDF!");
					}	
				}
				
				if (toeflScoreFile != null) {
					String contentType = toeflScoreFile.getContentType();
					if (contentType != null && !contentType.equals("application/pdf")) {
						throw new InvalidFileTypeException("Uploaded toefl file is not a PDF!");
					}	
				}
				
			}

			
			return ResponseEntity.ok("Application submitted successfully");

		} catch (

		InvalidFileTypeException e) {
			errorMessage = e.getMessage();
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		} catch (Exception e) {

			// Return ResponseEntity with status 500 (Internal Server Error)
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
