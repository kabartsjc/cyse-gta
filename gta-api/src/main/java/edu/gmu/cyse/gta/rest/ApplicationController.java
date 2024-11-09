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
			@RequestParam(value = "toeflScoreFile", required = false) MultipartFile toeflScoreFile) {

		try {
			Application application = null;

			// Process application data
			if (cvFile != null)
				System.out.println("Application Data:");

			if (applicationDataJson != null) {
				application = ApplicationJSONParser.parserApplication(applicationDataJson);
			}

			return ResponseEntity.ok("Application submitted successfully");

		} catch (Error error) {
			String errorMessage = "An unexpected error occurred.";

			// Return ResponseEntity with status 500 (Internal Server Error)
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
