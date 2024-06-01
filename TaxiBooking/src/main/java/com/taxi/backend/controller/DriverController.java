package com.taxi.backend.controller;

import com.taxi.backend.dao.model.DriverDTO;
import com.taxi.backend.dao.model.DriverRecordDTO;
import com.taxi.backend.dao.model.DriverUpdateDTO;
import com.taxi.backend.dao.request.DriverAvilableRequest;
import com.taxi.backend.dao.request.SetApprovalStatusDriver;
import com.taxi.backend.dao.request.SignUpRequest;
import com.taxi.backend.dao.response.MessageSourceResponse;
import com.taxi.backend.entities.Driver;
import com.taxi.backend.entities.User;
import com.taxi.backend.entities.Vehicle;
import com.taxi.backend.repository.DriverRepository;
import com.taxi.backend.service.DriverService;
import com.taxi.backend.service.VehicleService;
import com.taxi.backend.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/Driver")
@RequiredArgsConstructor
public class DriverController {
    private static ModelMapper modelMapper;
    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final DriverRepository driverRepository;

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

        return ResponseEntity.ok(drivers);
    }

    @PostMapping()
    public ResponseEntity<Driver> createDriver(@RequestBody DriverRecordDTO request) {

        var createDriver = driverService.create(request);
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
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<?> getDriverByUserId(@PathVariable Integer id) {Driver driver;
try {
    driver = driverService.findByUserId(id);
}catch (ResponseStatusException e){
    return new ResponseEntity<>(messageSource.getMessage("driver.notFound", null, Locale.getDefault()), HttpStatus.NOT_FOUND);
}

        return new ResponseEntity<>(driver, HttpStatus.OK);
    }
    @PostMapping("avilable")
    public ResponseEntity<?> setAvilable(@RequestBody DriverAvilableRequest driverAvilableRequest) {
        Driver driver;
        try {
            driver = driverService.open(driverAvilableRequest.getLocation(),driverAvilableRequest.getDriverId());
        }catch (ResponseStatusException e){
            return new ResponseEntity<>(messageSource.getMessage("driver.notFound", null, Locale.getDefault()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }
    @PostMapping("notavilable/{id}")
    public ResponseEntity<?> setNotAvilable(@PathVariable Integer id){
        Driver driver;
        try {
            driver = driverService.close(id);
        }catch (ResponseStatusException e){
            return new ResponseEntity<>(messageSource.getMessage("driver.notFound", null, Locale.getDefault()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }

    @PostMapping("uploadImage")
    public ResponseEntity<?> uploadImages(@RequestParam("images") List<MultipartFile> files,
                                          @RequestParam("id") Integer id) throws IOException {
        Driver driver=driverService.findOne(id);
        driver.setLisensePhoto(ImageUtils.compressImage(files.get(0).getBytes()));
        var vehicle=vehicleService.getVehicleById(driver.getVehicle().getId());
        vehicle.setCarPhoto(ImageUtils.compressImage(files.get(1).getBytes()));
        driverRepository.save(driver);
        vehicleService.save(vehicle);


        return new ResponseEntity<>("Upload image is completed", HttpStatus.OK);
    }
    @GetMapping("downloadVehicleImage/{id}")
    public ResponseEntity<?> downdloadVehicleImage ( @PathVariable  Integer id) throws IOException {
        Driver driver=driverService.findOne(id);
        Vehicle vehicle=vehicleService.getVehicleByDriver_Id(driver.getVehicle().getId());


        byte[] images=ImageUtils.compressImage(vehicle.getCarPhoto());



        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(images);
    }
    @GetMapping("downloadDriverImage/{id}")
    public ResponseEntity<?> downdloadDriverImage ( @PathVariable  Integer id) throws IOException {
        Driver driver=driverService.findOne(id);


        byte[] images=ImageUtils.compressImage(driver.getLisensePhoto());



        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(images);
    }
}
