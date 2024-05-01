package com.musala.drone.Dto.Response;

import com.musala.drone.Entity.DroneModel;
import com.musala.drone.Entity.DroneState;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DroneResponseDTO {

    private Long id;

    private String serialNumber;

    private DroneModel model;

    private double weight;

    private String batteryCapacity;

    private DroneState state;

    List<MedicationResponseDto> medication = new ArrayList<>();
}
