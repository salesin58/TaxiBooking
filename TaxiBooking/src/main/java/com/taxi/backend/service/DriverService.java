package com.taxi.backend.service;

import com.taxi.backend.dao.model.DriverRecordDTO;
import com.taxi.backend.dao.model.DriverUpdateDTO;
import com.taxi.backend.entities.Driver;
import com.taxi.backend.entities.DriverApprovalStatus;
import com.taxi.backend.entities.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DriverService {
    List<Driver> findAll();
    Driver findOne(Integer id);
    Driver save(Driver driver);
    Driver update(DriverUpdateDTO driverUpdateDTO);
    void deleteById(Integer id);
    Driver create(DriverRecordDTO driverRecordDTO);
    List<Driver> findAvailableDriver(String city, DriverApprovalStatus status,String vehicleType);

    Driver setDriverApprovalStatus(DriverApprovalStatus driverApprovalStatus, Integer driverId);

    Driver findByUserId(Integer userId);

    Driver open(Location location, Integer id);

    Driver close(Integer id);
}
