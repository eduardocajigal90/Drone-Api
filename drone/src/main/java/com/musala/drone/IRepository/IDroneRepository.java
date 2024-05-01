package com.musala.drone.IRepository;

import com.musala.drone.Entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDroneRepository extends JpaRepository<Drone, Long>,
        JpaSpecificationExecutor<Drone> {

    @Query("SELECT u FROM Drone u WHERE u.batteryCapacity > :capacity")
    List<Drone> findAllDrone(@Param("capacity") String capacity);
}