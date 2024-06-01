package com.taxi.backend.service.impl;

import com.taxi.backend.entities.Driver;
import com.taxi.backend.entities.ReviewDriver;
import com.taxi.backend.entities.TaxiBookingStatus;
import com.taxi.backend.entities.Vehicle;
import com.taxi.backend.repository.DriverRepository;
import com.taxi.backend.repository.ReviewDriverRepository;
import com.taxi.backend.repository.TaxiBookingRepository;
import com.taxi.backend.service.CustomerService;
import com.taxi.backend.service.ReviewDriverService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.management.relation.Relation;
import java.util.Optional;
import java.util.Set;
@Service
public class ReviewDriverServiceImpl implements ReviewDriverService {

    private final  DriverRepository driverRepository;
    private final ReviewDriverRepository driverReviewRepository;
    private final TaxiBookingRepository taxiBookingRepository;

    public ReviewDriverServiceImpl(DriverRepository driverRepository, ReviewDriverRepository driverReviewRepository, TaxiBookingRepository taxiBookingRepository) {
        this.driverRepository = driverRepository;
        this.driverReviewRepository = driverReviewRepository;
        this.taxiBookingRepository = taxiBookingRepository;
    }

    @Override
    public ReviewDriver getDriverReviewsofDriver(Integer id) {

        Optional<Driver> driverOptional= driverRepository.findById(id);
        if(driverOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Driver driver = driverOptional.get();
        ReviewDriver reviewDriver =  driverReviewRepository.findByDriver(driver);
        reviewDriver.setTotalCus(taxiBookingRepository.countAllRatingByDriver_Id(id, TaxiBookingStatus.COMPLETED));
        reviewDriver.setTotalPoint(taxiBookingRepository.sumAllRatingByDriver_Id(id,TaxiBookingStatus.COMPLETED)/ reviewDriver.getTotalCus());

        return driverReviewRepository.save(reviewDriver);
    }

    @Override
    public ReviewDriver saveDriverReview(ReviewDriver driverReview) {
        return driverReviewRepository.save(driverReview);
    }

    @Override
    public void deleteById(Integer Id) {
        driverReviewRepository.deleteById(Id);
    }
}
