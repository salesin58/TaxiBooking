package com.taxi.backend.controller;

import com.taxi.backend.dao.model.DriverDTO;
import com.taxi.backend.dao.model.DriverRecordDTO;
import com.taxi.backend.dao.model.DriverUpdateDTO;
import com.taxi.backend.dao.request.SetApprovalStatusDriver;
import com.taxi.backend.dao.request.SignUpRequest;
import com.taxi.backend.dao.response.MessageSourceResponse;
import com.taxi.backend.entities.Driver;
import com.taxi.backend.repository.DriverRepository;
import com.taxi.backend.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/Driver")
@RequiredArgsConstructor
public class DriverController {
    private static ModelMapper modelMapper;
    private final DriverService driverService;

    private final MessageSource messageSource;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Driver> findDriverById(@PathVariable Integer id) {

        Driver driver = driverService.findOne(id);
        var driverDTO = DriverDTO.builder().vehicle(driver.getVehicle()).user(driver.getUser()).approvalStatus(driver.getApprovalStatus()).isAvailable(driver.getIsAvailable()).activeCity(driver.getActiveCity())
                .lastKnownLocation(driver.getLastKnownLocation()).home(driver.getHome()).reviewDriver(driver.getReviewDriver()).build();
        return ResponseEntity.ok(driver);

    }

    @GetMapping
    public ResponseEntity<List<Driver>> getDrivers() {

        List<Driver> drivers = driverService.findAll();
        List<DriverDTO> dtos = drivers.stream()
                .map(driver -> modelMapper.map(driver, DriverDTO.class))
                .collect(Collectors.toList());


        return ResponseEntity.ok(drivers);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Driver> createDriver(@RequestBody DriverRecordDTO request,@RequestPart("licencePhoto") MultipartFile file,@RequestPart("vehiclePhoto") MultipartFile file2) {

        var createDriver = driverService.create(request,file,file2);
        var driverDTO = modelMapper.map(createDriver, DriverDTO.class);
        return ResponseEntity.ok(createDriver);

    }

  @PutMapping
    public ResponseEntity<?> updateDriver(@RequestBody DriverUpdateDTO driverUpdateDTO) {
        Driver driverToUpdate;
        try {
            driverToUpdate = driverService.update(driverUpdateDTO);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(messageSource.getMessage("driver.notFound", null, Locale.getDefault()), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(driverToUpdate, HttpStatus.OK);
    }
    @PostMapping(value = "/setapprovalstatus")
    public ResponseEntity<Driver> setApprovalStatus(@RequestBody SetApprovalStatusDriver setApprovalStatusDriver) {


        return ResponseEntity.ok(driverService.setDriverApprovalStatus(setApprovalStatusDriver.getDriverApprovalStatus(),setApprovalStatusDriver.getId()));

    }

}
