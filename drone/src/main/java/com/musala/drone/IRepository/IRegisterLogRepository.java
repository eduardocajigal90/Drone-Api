package com.musala.drone.IRepository;

import com.musala.drone.Entity.RegisterLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRegisterLogRepository extends JpaRepository<RegisterLog, Long>,
        JpaSpecificationExecutor<RegisterLog> {
    List<RegisterLog> findAllBySerialNumberIs(@Param("serialNumber") String serialNumber);
}