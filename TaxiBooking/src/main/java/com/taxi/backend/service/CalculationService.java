package com.taxi.backend.service;

import com.taxi.backend.entities.Location;
import com.taxi.backend.entities.VehicleType;

public interface CalculationService {
    public double calculateDistance(Location departure, Location destination);
    public double calculateEstimatedTime(double distance);
    public double calculateEstimatedPrice(VehicleType type, double distanceKm);

}
