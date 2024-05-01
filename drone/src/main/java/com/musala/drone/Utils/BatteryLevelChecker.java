package com.musala.drone.Utils;

import com.musala.drone.Entity.Drone;
import com.musala.drone.Entity.RegisterLog;
import com.musala.drone.IRepository.IDroneRepository;
import com.musala.drone.IRepository.IRegisterLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class BatteryLevelChecker {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatteryLevelChecker.class);

    @Autowired
    private IRegisterLogRepository registerLogRepository;

    @Autowired
    private IDroneRepository droneRepository;

    /**
     * Logic to check drone battery levels
     * Create corresponding history/audit event logs
     * Task scheduled to run every 2 minutes
     */
    @Scheduled(fixedRate = 120000)
    public void checkBatteryLevel() {
        List<Drone> droneList = droneRepository.findAll();
        if (droneList.isEmpty()) {
            LOGGER.info("No drone has been inserted or registered");
            return;
        }
        List<RegisterLog> registerLogs = new ArrayList<>();
        for (Drone drone : droneList) {
            RegisterLog registerLog = new RegisterLog();
            registerLog.setCurrentDate(LocalDateTime.now());
            registerLog.setModel(drone.getModel());
            registerLog.setSerialNumber(drone.getSerialNumber());
            registerLog.setBatteryCapacity(drone.getBatteryCapacity());
            registerLogs.add(registerLog);
        }
        registerLogRepository.saveAll(registerLogs);
        LOGGER.info("Registration in the database has been completed");
    }
}
