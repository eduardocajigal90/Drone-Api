package com.musala.drone.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "medication")
@Entity
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "name is required")
    @Pattern(regexp = "^[a-zA-Z0-9\\-_]+$", message = "Allowed only characters are letters, numbers, '-', and '_'")
    private String name;

    @Column(name = "weight")
    @Positive
    @NotNull(message = "weight is required")
    private double weight;

    @Column(name = "code")
    @NotEmpty(message = "code is required")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Allowed only characters are upper case letters, underscore, and numbers")
    private String code;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "drone_id")
    private Long droneId;

}
