package edu.gmu.cyse.gta.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateApplicationRequest {

    @Schema(example = "Buy two iPhones")
    @NotBlank
    private String description;
}
