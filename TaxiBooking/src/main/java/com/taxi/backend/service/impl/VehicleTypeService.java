package com.taxi.backend.service.impl;

import com.taxi.backend.entities.Location;
import com.taxi.backend.entities.VehicleType;
import com.taxi.backend.repository.VehicleTypeRepository;
import com.taxi.backend.service.IVehicleTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleTypeService implements IVehicleTypeService {
    private VehicleTypeRepository vehicleTypeRepository;

    public VehicleTypeService(VehicleTypeRepository vehicleTypeRepository) {
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    @Override
    public List<VehicleType> findAll() {
        return vehicleTypeRepository.findAll();
    }

    @Override
    public VehicleType findById(int theId) {
        Optional<VehicleType> found = vehicleTypeRepository.findById(theId);
        if (found.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return found.get();
    }

    @Override
    public VehicleType save(VehicleType vehicleType) {

        return vehicleTypeRepository.save(vehicleType);
    }

    @Override
    public void deleteById(int theId) {

    }
}
