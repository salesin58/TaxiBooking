package com.taxi.backend.service.impl;

import com.taxi.backend.entities.Customer;
import com.taxi.backend.entities.Vehicle;
import com.taxi.backend.repository.VehicleRepository;
import com.taxi.backend.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    @Override
    public Vehicle save(Vehicle vehicle) {
        return null;
    }

    @Override
    public Vehicle getVehicleByDriver_Id(Integer id) {
        return null;
    }

    @Override
    public Vehicle getVehicleById(Integer id) {
        Optional<Vehicle> result = vehicleRepository.findById(id);

        Vehicle vehicle = null;

        if (result.isPresent()) {
            vehicle = result.get();
        }
        else {
            throw new RuntimeException("Did not find vehicle id - " + id);
        }

        return vehicle;
    }
}
