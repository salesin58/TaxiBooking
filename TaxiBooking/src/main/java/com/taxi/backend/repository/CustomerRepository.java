package com.taxi.backend.repository;

import com.taxi.backend.entities.Customer;
import com.taxi.backend.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByUserId(Integer userId);
}
