package com.taxi.backend.service;

import com.taxi.backend.entities.Vehicle;

public interface VehicleService {
    Vehicle save(Vehicle vehicle);
    Vehicle getVehicleByDriver_Id(Integer id);

    Vehicle getVehicleById(Integer id);
}
