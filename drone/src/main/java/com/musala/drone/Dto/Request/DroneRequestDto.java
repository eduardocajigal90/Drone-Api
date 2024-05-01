package com.musala.drone.Dto.Request;

import com.musala.drone.Entity.DroneModel;
import com.musala.drone.Entity.DroneState;
import lombok.Data;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;


@Data
public class DroneRequestDto {

    @Pattern(regexp = "^[A-Z0-9]+$", message = "Allowed only numbers and uppercase letters")
    @Size(min = 1, max = 100, message = "The size must be between 1 and 100")
    @NotBlank(message = "Is required it cannot be null or empty")
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Is required it cannot be null or empty")
    private DroneModel model;

    @Max(value = 500, message = "The maximum weight allowed is 500 grams")
    @Positive(message = "The weight has to be a positive value and is required")
    @NotNull(message = "Is required it cannot be null or empty")
    private double weight;

    @Pattern(regexp = "^(?:100|[0-9]{1,2})%$", message = "The battery capacity must be a value between 0% and 100%")
    @NotBlank(message = "Is required it cannot be null or empty")
    private String batteryCapacity;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Is required it cannot be null or empty")
    private DroneState state;
}
