package com.musala.drone.Controller;

import com.musala.drone.Dto.Request.DroneRequestDto;
import com.musala.drone.Dto.Request.MedicationRequestDto;
import com.musala.drone.Dto.Response.DroneResponseDTO;
import com.musala.drone.Entity.Drone;
import com.musala.drone.Entity.Medication;
import com.musala.drone.Entity.RegisterLog;
import com.musala.drone.Service.DroneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/drones")
public class DispatchController {

    @Autowired
    private final DroneService droneService;

    @Operation(summary = "Logic to register a drone", description = "Apply the necessary validations and save the drone in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register drone"),
            @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject("{\n"
                                    + "    \"message\": \"Invalid data\",\n"
                                    + "    \"status\": 404,\n"
                                    + "    \"path\": \"/api/drones/\"\n"
                                    + "}")
                    })),
            @ApiResponse(responseCode = "500", description = "Internal server error",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject("{\n"
                                    + "    \"message\": \"Exception message\",\n"
                                    + "    \"status\": 500,\n"
                                    + "    \"path\": \"/api/drones/\"\n"
                                    + "}")
                    }))
    })
    @PostMapping
    public ResponseEntity<DroneResponseDTO> registerDrone(@Valid @RequestBody DroneRequestDto drone) {
        DroneResponseDTO registeredDrone = droneService.registerDrone(drone);
        return ResponseEntity.ok(registeredDrone);
    }

    @Operation(summary = "Logic to load medications on a drone", description = "Apply the necessary validations and update the status of the drone and medications in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loaded medications"),
            @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject("{\n"
                                    + "    \"message\": \"Invalid data\",\n"
                                    + "    \"status\": 404,\n"
                                    + "    \"path\": \"/api/drones/{droneId}/medications\"\n"
                                    + "}")
                    })),
            @ApiResponse(responseCode = "500", description = "Internal server error",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject("{\n"
                                    + "    \"message\": \"Exception message\",\n"
                                    + "    \"status\": 500,\n"
                                    + "    \"path\": \"/api/drones/{droneId}/medications\"\n"
                                    + "}")
                    }))
    })
    @PostMapping("/{droneId}/medications")
    public ResponseEntity<DroneResponseDTO> loadMedications( @Valid @PathVariable Long droneId, @Valid @RequestBody List<MedicationRequestDto> medications) throws IOException {
        DroneResponseDTO loadedDrone = droneService.loadMedications(droneId, medications);
        return ResponseEntity.ok(loadedDrone);
    }

    @Operation(summary = "Logic to add a medication to a drone with its respective image", description = "Apply the necessary validations and update the status of the drone and medications in the database, stores the image in the images directory.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added medication with image"),
            @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject("{\n"
                                    + "    \"message\": \"Invalid data\",\n"
                                    + "    \"status\": 404,\n"
                                    + "    \"path\": \"/api/drones/{droneId}/add-medication-with-image\"\n"
                                    + "}")
                    })),
            @ApiResponse(responseCode = "500", description = "Internal server error",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject("{\n"
                                    + "    \"message\": \"Exception message\",\n"
                                    + "    \"status\": 500,\n"
                                    + "    \"path\": \"/api/drones/{droneId}/add-medication-with-image\"\n"
                                    + "}")
                    }))
    })
    @PostMapping("/{droneId}/add-medication-with-image")
    public ResponseEntity<DroneResponseDTO> addMedication(@RequestParam("image") MultipartFile image,
                                                              @Valid @PathVariable Long droneId,
                                                              @Valid @ModelAttribute MedicationRequestDto medication) throws IOException {
        DroneResponseDTO loadedDrone = droneService.loadMedication(droneId, medication, image);
        return ResponseEntity.ok(loadedDrone);
    }

    @Operation(summary = "Logic to obtain medications loaded on a drone", description = "Obtains the drone from the database and returns the associated medications.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loaded medications "),
            @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject("{\n"
                                    + "    \"message\": \"Invalid data\",\n"
                                    + "    \"status\": 404,\n"
                                    + "    \"path\": \"/api/drones/{droneId}/medications\"\n"
                                    + "}")
                    })),
            @ApiResponse(responseCode = "500", description = "Internal server error",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject("{\n"
                                    + "    \"message\": \"Exception message\",\n"
                                    + "    \"status\": 500,\n"
                                    + "    \"path\": \"/api/drones/{droneId}/medications\"\n"
                                    + "}")
                    }))
    })
    @GetMapping("/{droneId}/medications")
    public ResponseEntity<List<Medication>> getLoadedMedications(@Valid @PathVariable Long droneId) {
        List<Medication> loadedMedications = droneService.getLoadedMedications(droneId);
        return ResponseEntity.ok(loadedMedications);
    }

    @Operation(summary = "Logic to obtain drones available for charging", description = "Obtains the drones from the database that meet the necessary conditions as capacity and status and returns them.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available drones "),
            @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject("{\n"
                                    + "    \"message\": \"Invalid data\",\n"
                                    + "    \"status\": 404,\n"
                                    + "    \"path\": \"/api/drones/available\"\n"
                                    + "}")
                    })),
            @ApiResponse(responseCode = "500", description = "Internal server error",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject("{\n"
                                    + "    \"message\": \"Exception message\",\n"
                                    + "    \"status\": 500,\n"
                                    + "    \"path\": \"/api/drones/available\"\n"
                                    + "}")
                    }))
    })
    @GetMapping("/available")
    public ResponseEntity<List<Drone>> getAvailableDrones() {
        // Lógica para obtener los drones disponibles para carga
        List<Drone> availableDrones = droneService.getAvailableDrones();
        return ResponseEntity.ok(availableDrones);
    }

    @Operation(summary = "Logic to obtain the battery level of a drone", description = "Get the drone from the database and return the battery level.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Battery level"),
            @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject("{\n"
                                    + "    \"message\": \"Invalid data\",\n"
                                    + "    \"status\": 404,\n"
                                    + "    \"path\": \"/api/drones/{droneId}/battery\"\n"
                                    + "}")
                    })),
            @ApiResponse(responseCode = "500", description = "Internal server error",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject("{\n"
                                    + "    \"message\": \"Exception message\",\n"
                                    + "    \"status\": 500,\n"
                                    + "    \"path\": \"/api/drones/{droneId}/battery\"\n"
                                    + "}")
                    }))
    })
    @GetMapping("/{droneId}/battery")
    public ResponseEntity<String> getBatteryLevel(@Valid @PathVariable Long droneId) {
        // Lógica para obtener el nivel de batería de un drone
        String batteryLevel = droneService.getBatteryLevel(droneId);
        return ResponseEntity.ok(batteryLevel);
    }

    @Operation(summary = "Change the status to loading", description = "Search for the drone associated with the droneId and check if the battery is below 25% and the status is inactive.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Changed status"),
            @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject("{\n"
                                    + "    \"message\": \"Invalid data\",\n"
                                    + "    \"status\": 404,\n"
                                    + "    \"path\": \"/api/drones/{droneId}/changeStateToLoading\"\n"
                                    + "}")
                    })),
            @ApiResponse(responseCode = "500", description = "Internal server error",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject("{\n"
                                    + "    \"message\": \"Exception message\",\n"
                                    + "    \"status\": 500,\n"
                                    + "    \"path\": \"/api/drones/{droneId}/changeStateToLoading\"\n"
                                    + "}")
                    }))
    })
    @GetMapping("/{droneId}/changeStateToLoading")
    public ResponseEntity<Drone> loadMedications(@Valid @PathVariable Long droneId) {
        // Lógica para cargar medicamentos en un drone
        Drone loadedDrone = droneService.changeStateToLoading(droneId);
        return ResponseEntity.ok(loadedDrone);
    }

    @Operation(summary = "List with all the log records stored", description = "List with all the log records stored.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List all log records stored"),
            @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject("{\n"
                                    + "    \"message\": \"Invalid data\",\n"
                                    + "    \"status\": 404,\n"
                                    + "    \"path\": \"/api/drones/getLogs\"\n"
                                    + "}")
                    })),
            @ApiResponse(responseCode = "500", description = "Internal server error",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject("{\n"
                                    + "    \"message\": \"Exception message\",\n"
                                    + "    \"status\": 500,\n"
                                    + "    \"path\": \"/api/drones/getLogs\"\n"
                                    + "}")
                    }))
    })
    @GetMapping("/getLogs")
    public ResponseEntity<List<RegisterLog>> getRegisterLogs() {
        return ResponseEntity.ok(droneService.registerLogs());
    }

    @Operation(summary = "List with all the log records stored in the table that correspond to serialNumber", description = "List with all the log records stored in the table that correspond to serialNumber.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List log records stored"),
            @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject("{\n"
                                    + "    \"message\": \"Invalid data\",\n"
                                    + "    \"status\": 404,\n"
                                    + "    \"path\": \"/api/drones/{serialNumber}/getLogsBySerialNumber\"\n"
                                    + "}")
                    })),
            @ApiResponse(responseCode = "500", description = "Internal server error",content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject("{\n"
                                    + "    \"message\": \"Exception message\",\n"
                                    + "    \"status\": 500,\n"
                                    + "    \"path\": \"/api/drones/{serialNumber}/getLogsBySerialNumber\"\n"
                                    + "}")
                    }))
    })
    @PostMapping("/{serialNumber}/getLogsBySerialNumber")
    public ResponseEntity<List<RegisterLog>> getLogsBySerialNumber(@Valid @PathVariable String serialNumber) {
        return ResponseEntity.ok(droneService.registerLogsBySerialNumber(serialNumber));
    }

}
