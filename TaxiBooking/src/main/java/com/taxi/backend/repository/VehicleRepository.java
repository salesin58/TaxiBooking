package com.taxi.backend.repository;

import com.taxi.backend.entities.User;
import com.taxi.backend.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    Vehicle findByDriverId(int driverId);
}
