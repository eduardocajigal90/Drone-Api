package com.musala.drone.IRepository;

import com.musala.drone.Entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface IMedicationRepository extends JpaRepository<Medication, Long>,
        JpaSpecificationExecutor<Medication> {
}