package com.taxi.backend.service;

import com.taxi.backend.entities.VehicleType;

import java.util.List;

public interface IVehicleTypeService {
    List<VehicleType> findAll();

    VehicleType findById(int theId);

    VehicleType save(VehicleType vehicleType);

    VehicleType findByName(String name);

    void deleteById(int theId);
    
}
