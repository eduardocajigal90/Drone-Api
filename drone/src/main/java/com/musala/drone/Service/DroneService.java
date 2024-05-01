package com.musala.drone.Service;

import com.musala.drone.Dto.Request.DroneRequestDto;
import com.musala.drone.Dto.Request.MedicationRequestDto;
import com.musala.drone.Dto.Response.DroneResponseDTO;
import com.musala.drone.Dto.Response.MedicationResponseDto;
import com.musala.drone.Entity.Drone;
import com.musala.drone.Entity.DroneState;
import com.musala.drone.Entity.Medication;
import com.musala.drone.Entity.RegisterLog;
import com.musala.drone.IRepository.IDroneRepository;
import com.musala.drone.IRepository.IRegisterLogRepository;
import com.musala.drone.IServices.IDroneService;
import com.musala.drone.Utils.Util;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DroneService implements IDroneService {

    @Autowired
    private IDroneRepository droneRepository;

    @Autowired
    private IRegisterLogRepository registerLogRepository;

    private ModelMapper modelMapper = new ModelMapper();

    private final Integer BATTERY_LEVEL  = 25;

    /**
     * Logic to register a drone
     * Apply the necessary validations and save the drone in the database
     * @param droneRequestDto
     * @return DroneResponseDTO
     */
    @Override
    public DroneResponseDTO registerDrone(DroneRequestDto droneRequestDto) {
        // Lógica para registrar un drone
        // Aplica las validaciones necesarias y guarda el drone en la base de datos
        Drone drone = modelMapper.map(droneRequestDto, Drone.class);
        droneRepository.save(drone);
        return modelMapper.map(drone, DroneResponseDTO.class);
    }

    /**
     * Logic to load medications on a drone
     * Apply the necessary validations and update the status of the drone and medications in the database
     * @param droneId
     * @param medicationsRequestDtoList
     * @return DroneResponseDTO
     * @exception EntityNotFoundException
     */
    @Override
    public DroneResponseDTO loadMedications(Long droneId, List<MedicationRequestDto> medicationsRequestDtoList) {
        Drone drone = getDrone(droneId);
        if (Objects.nonNull(drone) && drone.getWeight() >= Util.weightTotal(medicationsRequestDtoList)) {
            List<Medication> medicationList = modelMapper.map(
                    medicationsRequestDtoList,
                    new TypeToken<List<Medication>>() {}.getType());
            drone.getMedicationList().addAll(medicationList.stream()
                    .map(medication -> {
                        medication.setDroneId(drone.getId());
                        return medication;
                    })
                    .collect(Collectors.toList()));
            droneRepository.save(drone);
        }else{
            throw new EntityNotFoundException("The drone whit id: "+ droneId+" cannot be found or the weight of the medicines is greater than the weight of the drone: "+drone.getWeight());
        }
        return modelMapper.map(drone, DroneResponseDTO.class);
    }

    /**
     * Logic to add a medication to a drone with its respective image
     * @param droneId
     * @param medicationsRequestDto
     * @param image
     * @return DroneResponseDTO
     * @exception EntityNotFoundException
     * @exception IllegalArgumentException
     * @exception IOException
     */
    @Override
    public DroneResponseDTO loadMedication(Long droneId, MedicationRequestDto medicationsRequestDto, MultipartFile image) throws IOException {
        Drone drone = getDrone(droneId);
        if (Objects.isNull(drone) || drone.getWeight() < medicationsRequestDto.getWeight()) {
            throw new EntityNotFoundException("El dron con id: " + droneId + " no se encuentra o el peso de los medicamentos es mayor al peso del dron: " + drone.getWeight());
        }

        Medication medication = modelMapper.map(medicationsRequestDto, Medication.class);
        if (!image.isEmpty()) {
            medication.setImagePath(new Util().saveImage(image));
        }
        medication.setDroneId(droneId);
        drone.getMedicationList().add(medication);
        droneRepository.save(drone);

        DroneResponseDTO droneResponseDTO = modelMapper.map(drone, DroneResponseDTO.class);
        List<MedicationResponseDto> medicationResponseList = modelMapper.map(drone.getMedicationList(), new TypeToken<List<MedicationResponseDto>>() {}.getType());
        droneResponseDTO.setMedication(medicationResponseList);

        return droneResponseDTO;
    }

    /**
     * Logic to obtain medications loaded on a drone
     * Obtains the drone from the database and returns the associated medications
     * @param droneId
     * @return list of medication
     * @exception EntityNotFoundException
     */
    @Override
    public List<Medication> getLoadedMedications(Long droneId) {
        List<Medication> medicationList = getDrone(droneId).getMedicationList();
        if(medicationList.isEmpty() || Objects.isNull(medicationList))
            throw new EntityNotFoundException("There are no medications on the selected drone whit id: "+ droneId);
        return medicationList;
    }

    /**
     * Logic to obtain drones available for charging
     * Obtains the drones from the database that meet the necessary conditions and returns them
     * @return list of drones
     */
    @Override
    public List<Drone> getAvailableDrones() {
        List<Drone> droneList = droneRepository.findAllDrone(String.valueOf(BATTERY_LEVEL)+"%");
        return Util.getDronesWithGreaterWeight(droneList);
    }

    /**
     * Logic to obtain the battery level of a drone
     * Get the drone from the database and return the battery level
     * @param droneId
     * @return battery level
     * @exception EntityNotFoundException
     */
    @Override
    public String getBatteryLevel(Long droneId) {
        return getDrone(droneId).getBatteryCapacity();
    }

    /**
     * Search for the drone associated with the droneId and check if the battery is below 25% and the status is inactive.
     * If this is not the case, change the status to charging.
     * @param droneId
     * @return The drone with the status changed or an exception if it does not meet the requirements
     * @exception EntityNotFoundException
     */
    @Override
    public Drone changeStateToLoading(Long droneId){
        Drone drone = getDrone(droneId);
        if(Integer.parseInt(drone.getBatteryCapacity().replaceAll("[^0-9]", "")) < BATTERY_LEVEL || drone.getState() != DroneState.IDLE)
            throw new EntityNotFoundException("It is not possible to change the state drone whit id: "+ droneId);
        drone.setState(DroneState.LOADING);
        return droneRepository.save(drone);
    }

    /**
     * @return a list with all the log records stored
     */
    @Override
    public List<RegisterLog> registerLogs(){
        return registerLogRepository.findAll();
    }

    /**
     * @param serialNumber
     * @return a list with all the log records stored in the table that correspond to serialNumber
     */
    @Override
    public List<RegisterLog> registerLogsBySerialNumber(String serialNumber){
        return registerLogRepository.findAllBySerialNumberIs(serialNumber);
    }

    /**
     * looks for the drone associated with droneId and returns it
     * @param droneId
     * @return the drone associated with that id
     * @exception EntityNotFoundException
     */
    private Drone getDrone(Long droneId){
        return droneRepository.findById(droneId).orElseThrow(() -> new EntityNotFoundException("Drone with id:"+ droneId +" not found"));
    }
}
