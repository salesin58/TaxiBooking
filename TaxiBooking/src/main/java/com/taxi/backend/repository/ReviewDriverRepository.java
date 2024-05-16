package com.taxi.backend.repository;

import com.taxi.backend.entities.Driver;
import com.taxi.backend.entities.ReviewDriver;
import com.taxi.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ReviewDriverRepository extends JpaRepository<ReviewDriver, Integer> {
    ReviewDriver findByDriver(@Param("driver") Driver driver);


}
