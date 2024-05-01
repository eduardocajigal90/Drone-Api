package com.musala.drone.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import com.musala.drone.Entity.Medication;
import com.musala.drone.Entity.Drone;
import com.musala.drone.Dto.Request.MedicationRequestDto;
import org.springframework.web.multipart.MultipartFile;

public class Util {

    final Path rootFolder = Paths.get("images");

    public static double weightTotal(List<MedicationRequestDto> medicationList) {
        double totalWeight = medicationList.stream()
                .mapToDouble(MedicationRequestDto::getWeight)
                .sum();

        return totalWeight;
    }

    public static List<Drone> getDronesWithGreaterWeight(List<Drone> droneList) {
        List<Drone> filteredDrones = droneList.stream()
                .filter(drone -> drone.getMedicationList()
                        .stream()
                        .mapToDouble(Medication::getWeight)
                        .sum() < drone.getWeight())
                .collect(Collectors.toList());

        return filteredDrones;
    }

    public String saveImage(MultipartFile file) throws IOException {
        validateFile(file);
        if (!Files.exists(rootFolder)) {
            Files.createDirectories(rootFolder);
        }
        Path filePath = this.rootFolder.resolve(file.getOriginalFilename());
        if (!Files.exists(filePath)) {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        return filePath.toFile().getAbsolutePath();
    }

    private void validateFile(MultipartFile file) {
        String nameFile = file.getOriginalFilename();
        if (nameFile == null || nameFile.isEmpty() || !nameFile.matches(".*\\.(jpg|png|JPG|PNG)$")) {
            throw new IllegalArgumentException("The file is invalid. It must be of type .jpg or .png");
        }
    }
}
