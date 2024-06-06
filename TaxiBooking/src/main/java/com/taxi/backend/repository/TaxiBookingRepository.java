package com.taxi.backend.repository;

import com.taxi.backend.entities.TaxiBooking;
import com.taxi.backend.entities.TaxiBookingStatus;
import com.taxi.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TaxiBookingRepository extends JpaRepository<TaxiBooking, Integer> {
    Set<TaxiBooking> findAllByDriver_Id(@Param("id") Integer id);
    @Query("SELECT sum(t.rating) FROM TaxiBooking t WHERE t.driver.id = :id AND t.taxibookingStatus = :status")
    Integer sumAllRatingByDriver_Id(@Param("id") Integer id, @Param("status") TaxiBookingStatus status);
    @Query("SELECT COUNT(t.rating) FROM TaxiBooking t WHERE t.driver.id = :id AND t.taxibookingStatus = :status")
    Integer countAllRatingByDriver_Id(@Param("id") Integer id, @Param("status") TaxiBookingStatus status);



    List<TaxiBooking> findAllByCustomer_IdAndTaxibookingStatus(Integer Customer_Id, TaxiBookingStatus taxiBookingStatus);
    @Query("SELECT r from TaxiBooking r WHERE r.customer.id=:id and (r.taxibookingStatus=:status1 or r.taxibookingStatus=:status2 or r.taxibookingStatus=:status3 )")
   List<TaxiBooking> findStatusTaxiBookingByid(@Param("id") Integer id, @Param("status1") TaxiBookingStatus status1, @Param("status2") TaxiBookingStatus status2, @Param("status3") TaxiBookingStatus status3);
}
