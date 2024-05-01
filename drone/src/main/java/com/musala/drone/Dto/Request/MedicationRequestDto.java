package com.musala.drone.Dto.Request;

import lombok.Data;
import javax.validation.constraints.*;

@Data
public class MedicationRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9\\-_]+$", message = "Allowed only characters are letters, numbers, '-', and '_': is required")
    @NotBlank(message = "Is required it cannot be null or empty")
    private String name;

    @Positive(message = "The weight has to be a positive value and is required")
    @NotNull(message = "Is required it cannot be null or empty")
    private double weight;

    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Allowed only characters are upper case letters, underscore, and numbers: is required")
    @NotBlank(message = "Is required it cannot be null or empty")
    private String code;

}
