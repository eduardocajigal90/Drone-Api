package com.musala.drone.IServices;

import com.musala.drone.Dto.Request.DroneRequestDto;
import com.musala.drone.Dto.Request.MedicationRequestDto;
import com.musala.drone.Dto.Response.DroneResponseDTO;
import com.musala.drone.Entity.Drone;
import com.musala.drone.Entity.Medication;
import com.musala.drone.Entity.RegisterLog;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface IDroneService {

    DroneResponseDTO registerDrone(DroneRequestDto droneRequestDto);

    DroneResponseDTO loadMedications(Long droneId, List<MedicationRequestDto> medicationsRequestDtoList);

    DroneResponseDTO loadMedication(Long droneId, MedicationRequestDto medicationsRequestDto, MultipartFile image)throws IOException;

    List<Medication> getLoadedMedications(Long droneId);

    List<Drone> getAvailableDrones();

    String getBatteryLevel(Long droneId);

    Drone changeStateToLoading(Long droneId);

    List<RegisterLog> registerLogsBySerialNumber(String serialNumber);

    List<RegisterLog> registerLogs();
}
