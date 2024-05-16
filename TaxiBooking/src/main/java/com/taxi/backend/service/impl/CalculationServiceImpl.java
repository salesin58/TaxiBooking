package com.taxi.backend.service.impl;

import com.taxi.backend.entities.Location;
import com.taxi.backend.entities.VehicleType;
import com.taxi.backend.service.CalculationService;
import org.springframework.stereotype.Service;

@Service
public class CalculationServiceImpl implements CalculationService {
    public final static double AVERAGE_RADIUS_OF_EARTH = 6371;
    private final static double AVERAGE_SPEED_IN_KILOMETERS = 50;
    @Override
    public double calculateDistance(Location departure, Location destination) {
        double latDistance = Math.toRadians(departure.getLatitude() - destination.getLatitude());
        double lngDistance = Math.toRadians(departure.getLongitude() - destination.getLongitude());

        double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
                (Math.cos(Math.toRadians(departure.getLatitude()))) *
                        (Math.cos(Math.toRadians(destination.getLatitude()))) *
                        (Math.sin(lngDistance / 2)) *
                        (Math.sin(lngDistance / 2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return AVERAGE_RADIUS_OF_EARTH * c;
    }

    @Override
    public double calculateEstimatedTime(double distance) {
        return distance / AVERAGE_SPEED_IN_KILOMETERS * 60;
    }

    @Override
    public double calculateEstimatedPrice(VehicleType type, double distanceKm) {
        return type.getPricePerKm() + distanceKm * 120;
    }
}
