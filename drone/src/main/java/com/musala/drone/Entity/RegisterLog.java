package com.musala.drone.Entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table(name = "register_log")
@Entity
public class RegisterLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number")
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "drone_model")
    private DroneModel model;

    @Column(name = "current_date_time")
    private LocalDateTime currentDate;

    @Column(name = "battery_capacity")
    private String batteryCapacity;
}
