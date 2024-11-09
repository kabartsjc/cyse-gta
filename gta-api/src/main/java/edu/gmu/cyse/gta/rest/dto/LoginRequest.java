package edu.gmu.cyse.gta.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @Schema(example = "user")
    @NotBlank
    private String username;

    @Schema(example = "password")
    @NotBlank
    private String password;
}
