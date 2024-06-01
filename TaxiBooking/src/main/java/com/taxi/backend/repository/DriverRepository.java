package com.taxi.backend.repository;

import com.taxi.backend.entities.Driver;
import com.taxi.backend.entities.DriverApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    @Query("SELECT d FROM Driver d WHERE d.vehicle.carType.name=:vehicleType and d.isAvailable=true and d.activeCity=:activeCity and d.approvalStatus=:approvalStatus "  )
    List<Driver> findAllByActiveCityAndIsAvailableTrueAndApprovalStatus(String activeCity, DriverApprovalStatus approvalStatus,String vehicleType);
    Driver findByUserId(Integer userId);
}
