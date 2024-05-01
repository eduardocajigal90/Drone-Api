package com.musala.drone.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "drone")
@Entity
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "serialNumber is required")
    @Size(min = 1, max = 100, message = "The size must be between 1 and 100")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Allowed only numbers and uppercase letters")
    @Column(name = "serial_number", unique = true)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "drone_model")
    @NotNull(message = "model is required")
    private DroneModel model;

    @Column(name = "weight_limit")
    @NotNull(message = "weightLimit is required")
    @Positive
    @Max(value = 500, message = "The maximum weight allowed is 500 grams")
    private double weight;

    @Column(name = "battery_capacity")
    @NotEmpty(message = "batteryCapacity is required")
    @Pattern(regexp = "^(?:100|[0-9]{1,2})%$", message = "The battery capacity must be a value between 0 and 100")
    private String batteryCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    @NotNull(message = "state is required")
    private DroneState state;

    @OneToMany(mappedBy = "droneId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Medication> medicationList = new ArrayList<>();



}
