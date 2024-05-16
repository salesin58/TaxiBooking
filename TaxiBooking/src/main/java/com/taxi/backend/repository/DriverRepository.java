package com.taxi.backend.repository;

import com.taxi.backend.entities.Driver;
import com.taxi.backend.entities.DriverApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    Optional<Driver> getDriversByVehicle_Id(@Param("id") Integer id);
    Set<Driver> getAllByIsAvailableTrue();
    List<Driver> getDriversByApprovalStatus(DriverApprovalStatus approvalStatus);
    List<Driver> findByActiveCityAndIsAvailableTrueAndApprovalStatus(String activeCity, DriverApprovalStatus approvalStatus);
}
