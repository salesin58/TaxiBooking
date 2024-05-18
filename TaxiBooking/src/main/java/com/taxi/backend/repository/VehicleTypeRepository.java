package com.taxi.backend.repository;

import com.taxi.backend.entities.User;
import com.taxi.backend.entities.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer> {
    VehicleType findByName(String name);
}
