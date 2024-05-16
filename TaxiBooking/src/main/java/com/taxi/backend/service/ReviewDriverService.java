package com.taxi.backend.service;

import com.taxi.backend.entities.ReviewDriver;

import java.util.Set;

public interface ReviewDriverService {
    ReviewDriver getDriverReviewsofDriver(Integer id);
    ReviewDriver saveDriverReview(ReviewDriver driverReview);
    void deleteById(Integer Id);

}
