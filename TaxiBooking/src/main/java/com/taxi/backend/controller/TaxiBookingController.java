package com.taxi.backend.controller;

import com.taxi.backend.dao.request.*;
import com.taxi.backend.dao.response.TaxiBookingIdResponse;
import com.taxi.backend.entities.TaxiBooking;
import com.taxi.backend.service.TaxiBookingService;
import com.taxi.backend.utils.ChatGptUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/TaxiBooking")
@RequiredArgsConstructor
public class TaxiBookingController {
    private final TaxiBookingService taxiBookingService;
    private final MessageSource messageSource;


    @GetMapping(value = "/{id}")
    public ResponseEntity<TaxiBooking> findTaxiBookingById(@PathVariable Integer id) {
        return ResponseEntity.ok(taxiBookingService.findTaxiBookingById(id));
    }

    @GetMapping
    public ResponseEntity<List<TaxiBooking>> getAllTaxiBooking() {
        return ResponseEntity.ok(taxiBookingService.alltrip());
    }

    @PostMapping
    public ResponseEntity<TaxiBooking> createTaxiBooking(@RequestBody TaxiBookingCreateRequest request) {


        return ResponseEntity.ok(taxiBookingService.AddTrip(request));

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateDriver(@RequestBody TaxiBooking tb,@PathVariable Integer id) {
        TaxiBooking taxiBookingToUpdate;
        try {
            taxiBookingToUpdate = taxiBookingService.updateTrip( tb, id);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(messageSource.getMessage("taxiBooking.notFound", null, Locale.getDefault()), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(taxiBookingToUpdate, HttpStatus.OK);
    }
   @PostMapping(value = "/driver/find/{id}")
    public ResponseEntity<?> findingSuitableDrivers(@PathVariable Integer id) {
var taxiBooking = taxiBookingService.findTaxiBookingById(id);
     return new ResponseEntity<>(taxiBookingService.findcloseSuitableDriver(taxiBooking), HttpStatus.OK);
    }
    @PostMapping(value = "/driver/deliver")
    public ResponseEntity<?> deliverTaxiBooking(@RequestBody deliverDTO tb) {

        return new ResponseEntity<>(taxiBookingService.deliverTaxiBooking(tb.getTaxiId(), tb.getDriverId()),HttpStatus.OK);
    }

    @PostMapping("/chatgpt")
    public ResponseEntity<?> requestFromChatgpt(@RequestBody RequestChatGpt tb) {
        try {
            return new ResponseEntity<>(ChatGptUtils.sendMessage(tb.getMessage()),HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("payment")
    public ResponseEntity<?> payment(@RequestBody PaymentRequest paymentRequest){

        String taxiBookingToPayment;
        try {
            taxiBookingToPayment = taxiBookingService.payment(paymentRequest.getAmount(),paymentRequest.getId());
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(messageSource.getMessage("taxiBooking.notFound", null, Locale.getDefault()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(taxiBookingToPayment, HttpStatus.OK);
    }
    @PostMapping("endbooking")
    public ResponseEntity<?> endbooking(@RequestBody TaxiBookingEndRequest taxiBookingEndRequest){
        return new ResponseEntity<> (taxiBookingService.tripEnd(taxiBookingEndRequest), HttpStatus.OK);

    }
    @PostMapping("rideendstartstatus")
    public ResponseEntity<?> statusEndStartRide(@RequestBody StatusRideRequest statusRideRequest) {
        return new ResponseEntity<> (taxiBookingService.findStatusTaxiBookingByUserId(statusRideRequest.getId(),statusRideRequest.getStatus()), HttpStatus.OK);
    }
    @GetMapping("ridestatus/{id}")
    public ResponseEntity<?> statusRide(@PathVariable Integer id) {
        return new ResponseEntity<> (taxiBookingService.findRideStatusTaxiBookingByCusId(id), HttpStatus.OK);

    }
    @GetMapping("alltripdriverıd/{id}")
    public ResponseEntity<?> alltripDriver(@PathVariable Integer id)
    {
        return new ResponseEntity<>(taxiBookingService.allTripDriverId(id), HttpStatus.OK);

    }
    @PostMapping("settaxibookingstatus")
    public ResponseEntity<?> setTaxiBookingStatus(@RequestBody StatusRideRequest statusRideRequest){
        return new ResponseEntity<>(taxiBookingService.setTaxiBookingStatus(statusRideRequest.getStatus(),statusRideRequest.getId()), HttpStatus.OK);
    }
    @GetMapping("get/{id}")
    public ResponseEntity<?> findTaxiBookingIdsById(@PathVariable Integer id) {
        var taxiBooking= taxiBookingService.findTaxiBookingById(id);
        var taxiBookingToReturn = TaxiBookingIdResponse.builder().taxiBookingId(taxiBooking.getId())
                .taxiCustomerId(taxiBooking.getCustomer().getId()).taxiDriverId(taxiBooking.getDriver().getId())
                .taxiVehicleTypeId(taxiBooking.getVehicleType().getId()).build();
        return ResponseEntity.ok(taxiBookingToReturn);
    }
}
