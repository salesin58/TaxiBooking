package com.taxi.backend.controller;

import com.taxi.backend.dao.model.DriverDTO;
import com.taxi.backend.dao.model.DriverRecordDTO;
import com.taxi.backend.dao.model.DriverUpdateDTO;
import com.taxi.backend.dao.model.VehicleTypeDTO;
import com.taxi.backend.entities.Driver;
import com.taxi.backend.entities.VehicleType;
import com.taxi.backend.service.DriverService;
import com.taxi.backend.service.impl.VehicleTypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/VehicleType")
@RequiredArgsConstructor
public class VehicleTypeController {
    private final VehicleTypeService vehicleTypeService;
    private static ModelMapper modelMapper;
    private final DriverService driverService;

    private final MessageSource messageSource;

    @GetMapping(value = "/{id}")
    public ResponseEntity<VehicleType> findVehicleTypeById(@PathVariable Integer id) {

        var vehicleType = vehicleTypeService.findById(id);

        return ResponseEntity.ok(vehicleType);

    }

    @GetMapping
    public ResponseEntity<List<VehicleType>> getVehicleType() {

        List<VehicleType> vehicleTypes = vehicleTypeService.findAll();


        return ResponseEntity.ok(vehicleTypes);
    }

    @PostMapping
    public ResponseEntity<VehicleType> createDriver(@RequestBody VehicleTypeDTO request) {
        var vehicleType = modelMapper.map(request, VehicleType.class);
        var createDriver = vehicleTypeService.save(vehicleType);
        return ResponseEntity.ok(createDriver);

    }




}
