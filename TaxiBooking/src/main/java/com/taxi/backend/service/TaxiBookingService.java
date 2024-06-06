package com.taxi.backend.service;

import com.taxi.backend.dao.model.DriverDistanceDTO;
import com.taxi.backend.dao.request.TaxiBookingCreateRequest;
import com.taxi.backend.dao.request.TaxiBookingEndRequest;
import com.taxi.backend.entities.Driver;
import com.taxi.backend.entities.TaxiBooking;
import com.taxi.backend.entities.TaxiBookingStatus;

import java.util.List;
import java.util.Set;

public interface TaxiBookingService {

    public TaxiBooking AddTrip(TaxiBookingCreateRequest tb);
    public List<TaxiBooking> alltrip();
    public TaxiBooking updateTrip(TaxiBooking tb,Integer id);

    List<Driver> findSuitableDriver(TaxiBooking taxiBooking);

    List<DriverDistanceDTO> findcloseSuitableDriver(TaxiBooking taxiBooking);

    TaxiBooking deliverTaxiBooking(Integer taxiId, Integer driverId);

    public String deletetrip(Integer id) ;
    public TaxiBooking tripEnd(TaxiBookingEndRequest taxiBookingEndRequest);

    TaxiBooking findTaxiBookingById(int id);

    TaxiBooking setTaxiBookingStatus(TaxiBookingStatus taxiBookingStatus, Integer id);

    Set<TaxiBooking> allTripDriverId(Integer driverId);

    String payment(int amount, Integer id);

    List<TaxiBooking> findStatusTaxiBookingByUserId(Integer userId,TaxiBookingStatus taxiBookingStatus);

    List<TaxiBooking> findRideStatusTaxiBookingByCusId(Integer cusId);
}
