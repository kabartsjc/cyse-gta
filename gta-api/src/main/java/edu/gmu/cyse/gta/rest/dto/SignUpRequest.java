package edu.gmu.cyse.gta.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequest {

    @Schema(example = "user3")
    @NotBlank
    private String username;

    @Schema(example = "user3")
    @NotBlank
    private String password;

    @Schema(example = "User 3 user")
    @NotBlank
    private String name;
    
    @Schema(example = "G12344")
    @NotBlank
    private String gmuID;
    
    
    @Schema(example = "user3@mycompany.com")
    @Email
    private String email;
}
