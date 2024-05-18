package com.taxi.backend.repository;

import com.taxi.backend.entities.TaxiBooking;
import com.taxi.backend.entities.TaxiBookingStatus;
import com.taxi.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TaxiBookingRepository extends JpaRepository<TaxiBooking, Integer> {
    Set<TaxiBooking> findAllByDriver_Id(@Param("id") Integer id);
    @Query("select sum(t.rating) FROM TaxiBooking t WHERE t.driver.id =:id  ")
    Integer sumAllRatingByDriver_Id(@Param("id") Integer id);
    @Query("select count(t.rating) FROM TaxiBooking t WHERE t.driver.id =:id  ")
    Integer countAllRatingByDriver_Id(@Param("id") Integer id);

   List<TaxiBooking> findAllByCustomerIdAndTaxibookingStatus(@Param("id") Integer id, TaxiBookingStatus status);
}
