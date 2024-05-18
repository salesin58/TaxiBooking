package com.taxi.backend.service.impl;

import com.taxi.backend.dao.model.DriverRecordDTO;
import com.taxi.backend.dao.model.DriverUpdateDTO;
import com.taxi.backend.entities.*;
import com.taxi.backend.repository.DriverRepository;
import com.taxi.backend.service.DriverService;
import com.taxi.backend.service.ReviewDriverService;
import com.taxi.backend.service.UserService;
import com.taxi.backend.service.VehicleService;
import io.jsonwebtoken.lang.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final UserService userService;
    private final ReviewDriverService reviewDriverService;
    private final LocationService locationService;
    private final VehicleTypeService vehicleTypeService;
    private final VehicleService vehicleService;

    public DriverServiceImpl(DriverRepository driverRepository, UserService userService, ReviewDriverService reviewDriverService, LocationService locationService, VehicleTypeService vehicleTypeService, VehicleService vehicleService) {
        this.driverRepository = driverRepository;
        this.userService = userService;
        this.reviewDriverService = reviewDriverService;
        this.locationService = locationService;
        this.vehicleTypeService = vehicleTypeService;
        this.vehicleService = vehicleService;
    }

    @Override
    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    @Override
    public Driver findOne(Integer id) {
        Optional<Driver> found = driverRepository.findById(id);
        if (found.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return found.get();
    }

    @Override
    public Driver save(Driver driver) {
        User user = userService.findById(driver.getUser().getId());
        user.setRole(Role.DRIVER);
        userService.saved(user);
        return driverRepository.save(driver);
    }

    @Override
    public Driver update(DriverUpdateDTO driverUpdateDTO) {
        var driver=findOne(driverUpdateDTO.getId());
        var locationHome=locationService.findLocationById(driver.getHome().getId());
        var locationLastKnown=locationService.findLocationById(driver.getLastKnownLocation().getId());
        locationHome.setAddress(driverUpdateDTO.getAddressHome());
        locationHome.setLatitude(driverUpdateDTO.getLatitudeHome());
        locationHome.setLongitude(driverUpdateDTO.getLongitudeHome());
        locationService.save(locationHome);
        locationLastKnown.setAddress(driverUpdateDTO.getAddressLastKnown());
        locationLastKnown.setLatitude(driverUpdateDTO.getLatitudeLastKnown());
        locationLastKnown.setLongitude(driverUpdateDTO.getLongitudeLastKnown());
        locationService.save(locationLastKnown);
        driver.setLicenseDetails(driverUpdateDTO.getLicenseDetails());
        driver.setActiveCity(driverUpdateDTO.getActiveCity());
        Vehicle  vehicle=vehicleService.getVehicleById(driver.getVehicle().getId());
        vehicle.setColor(driverUpdateDTO.getColor());
        var vehicleType=vehicleTypeService.findById(driverUpdateDTO.getCarType());
        vehicle.setCarType(vehicleType);
        vehicle.setPlateNo(driverUpdateDTO.getPlateNo());
        vehicle.setBrandAndModel(driverUpdateDTO.getBrandAndModel());
        driver.setVehicle(vehicle);
        vehicleService.save(vehicle);
        return save(driver);

    }

    @Override
    public void deleteById(Integer id) {
        Driver driverFound = findOne(id);
        User userDriver = userService.findById(driverFound.getUser().getId());
        ReviewDriver reviewDriver = reviewDriverService.getDriverReviewsofDriver(id);
        if (reviewDriver != null) {
            driverFound.setReviewDriver(null);
            reviewDriverService.deleteById(reviewDriver.getId());
        }
        userDriver.setRole(Role.USER);
        userService.saved(userDriver);
        driverRepository.deleteById(id);
    }

    @Override
    public Driver create(DriverRecordDTO driverRecordDTO) {

        var locationHome = Location.builder().latitude(driverRecordDTO.getLatitude())
                .longitude(driverRecordDTO.getLongitude())
                .address(driverRecordDTO.getAddress())
                .build();
        var locationLastKnown = Location.builder().latitude(driverRecordDTO.getLatitude())
                .longitude(driverRecordDTO.getLongitude())
                .address(driverRecordDTO.getAddress())
                .build();

            var vehicleType=vehicleTypeService.findById(driverRecordDTO.getCarType());

        var locationHome_ = locationService.save(locationHome);
        var locationLastKnown_ = locationService.save(locationLastKnown);
        var vehicle = Vehicle.builder().brandAndModel(driverRecordDTO.getBrandAndModel()).color(driverRecordDTO.getColor()).carType(vehicleType).build();
        var reviewDriver = ReviewDriver.builder().build();
        var driver = Driver.builder().home(locationHome_)
                .lastKnownLocation(locationLastKnown_)
                .reviewDriver(reviewDriver)
                .isAvailable(false)
                .approvalStatus(DriverApprovalStatus.PENDING)
                .LicenseDetails(driverRecordDTO.getLicenseDetails())
                .vehicle(vehicle)
                .activeCity(driverRecordDTO.getActiveCity()).build();



        reviewDriver.setDriver(driver);
        vehicle.setDriver(driver);
        save(driver);
        User user = userService.findById(driver.getUser().getId());
        user.setRole(Role.DRIVER);
        userService.saved(user);

        return driver;
    }

    @Override
    public List<Driver> findAvailableDriver(String city,DriverApprovalStatus Status,String vehicleType) {
        return driverRepository.findByActiveCityAndIsAvailableTrueAndApprovalStatus(city,Status,vehicleType);
    }
}
