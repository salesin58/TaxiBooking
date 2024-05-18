package com.taxi.backend.service.impl;

import com.taxi.backend.dao.model.DriverDistanceDTO;
import com.taxi.backend.dao.request.TaxiBookingCreateRequest;
import com.taxi.backend.dao.request.TaxiBookingEndRequest;
import com.taxi.backend.entities.*;
import com.taxi.backend.repository.CustomerRepository;
import com.taxi.backend.repository.DriverRepository;
import com.taxi.backend.repository.TaxiBookingRepository;
import com.taxi.backend.service.CalculationService;
import com.taxi.backend.service.DriverService;
import com.taxi.backend.service.IVehicleTypeService;
import com.taxi.backend.service.TaxiBookingService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class TaxiBookingServiceImpl implements TaxiBookingService {
    private final CustomerRepository customerRepository;
    private final TaxiBookingRepository taxiBookingRepository;
    private final DriverRepository driverRepository;
    private final CalculationService calculationService;
    private final DriverService driverService;
    private final IVehicleTypeService vehicleTypeService;

    public TaxiBookingServiceImpl(CustomerRepository customerRepository, TaxiBookingRepository taxiBookingRepository, DriverRepository driverRepository, CalculationService calculationService, DriverService driverService, IVehicleTypeService vehicleTypeService) {
        this.customerRepository = customerRepository;
        this.taxiBookingRepository = taxiBookingRepository;
        this.driverRepository = driverRepository;
        this.calculationService = calculationService;
        this.driverService = driverService;
        this.vehicleTypeService = vehicleTypeService;
    }

    @Override
    public TaxiBooking AddTrip(TaxiBookingCreateRequest tb) {
        List<Location> route = List.of(tb.getToLocation(), tb.getFromLocation())    ;
        var findCustomer=customerRepository.findById(tb.getCustomerId());
        var findVehicleTypeByname=vehicleTypeService.findByName(tb.getVehicleType());
        var findCustom=findCustomer.get();
        Date now = new Date();
        var taxiBooking = TaxiBooking.builder().route(route).customer(findCustom).startTime(now)
                .totalDistanceMeters(tb.getTotalDistanceMeters()).customer(findCustom)
                .TaxibookingStatus(TaxiBookingStatus.SCHEDULE).vehicleType(findVehicleTypeByname).city(tb.getCity()).build();

        return taxiBookingRepository.save(taxiBooking);
    }

    @Override
    public List<TaxiBooking> alltrip() {
        return taxiBookingRepository.findAll();
    }

    @Override
    public TaxiBooking updateTrip(TaxiBooking tb, Integer id) {
        return null;
    }
    @Override
    public List<Driver> findSuitableDriver(TaxiBooking taxiBooking){
        return driverRepository.findByActiveCityAndIsAvailableTrueAndApprovalStatus(taxiBooking.getCity(), DriverApprovalStatus.APPROVED,taxiBooking.getVehicleType().getName());
    }
    @Override
    public List<DriverDistanceDTO> findcloseSuitableDriver(TaxiBooking taxiBooking){
        List<Driver> drivers=findSuitableDriver(taxiBooking);
        List<DriverDistanceDTO> driverDistanceDTOs=new ArrayList<>();
        for (int i = 0; i < drivers.size(); i++) {
            DriverDistanceDTO distanceDTO=DriverDistanceDTO.builder().driver(drivers.get(i))
                    .distance(calculationService.calculateDistance(drivers.get(i).getLastKnownLocation()
                            ,taxiBooking.getRoute().get(0))).build();
            distanceDTO.setTime(calculationService.calculateEstimatedTime(distanceDTO.getDistance()));
            if (!taxiBooking.getCustomer().getUser().getId().equals(distanceDTO.getDriver().getUser().getId())){
            driverDistanceDTOs.add(distanceDTO);}
        }
        Collections.sort(driverDistanceDTOs);
        return driverDistanceDTOs.subList(0, Math.min(driverDistanceDTOs.size(), 5));

    }
@Override
    public TaxiBooking deliverTaxiBooking(Integer taxiId, Integer driverId) {
        var taxibooking=findTaxiBookingById(taxiId);
        var driver=driverService.findOne(driverId);
        taxibooking.setDriver(driver);
        taxibooking.setTaxibookingStatus(TaxiBookingStatus.CAB_DELIVERED);
        driver.setIsAvailable(false);
        return taxiBookingRepository.save(taxibooking);
    }

    @Override
    public String deletetrip(Integer id) {
        return "";
    }
    @Override
    public TaxiBooking findTaxiBookingById(int id) {
        Optional<TaxiBooking> found = taxiBookingRepository.findById(id);
        if (found.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return found.get();
    }
    @Override
    public TaxiBooking tripEnd(TaxiBookingEndRequest taxiber) {
      var taxiBooking=findTaxiBookingById(taxiber.getId());
      taxiBooking.setRating(taxiber.getRating());
      taxiBooking.setEndTime(new Date());
      taxiBooking.setTaxibookingStatus(TaxiBookingStatus.COMPLETED);
      var driver =driverService.findOne(taxiBooking.getDriver().getId());
      driver.setIsAvailable(true);
      driverService.save(driver);
        return taxiBookingRepository.save(taxiBooking);
    }
    public TaxiBooking setTaxiBookingStatus(TaxiBookingStatus taxiBookingStatus,Integer id)
    {
        var taxiBooking=findTaxiBookingById(id);
        taxiBooking.setTaxibookingStatus(taxiBookingStatus);
        return taxiBookingRepository.save(taxiBooking);
    }
    @Override
    public Set<TaxiBooking> allTripDriverId(Integer driverId){
        return  taxiBookingRepository.findAllByDriver_Id(driverId);
    }
}
