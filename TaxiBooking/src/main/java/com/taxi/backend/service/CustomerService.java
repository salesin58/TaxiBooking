package com.taxi.backend.service;

import com.taxi.backend.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomerService {
    Customer save(Customer customer) ;
    Customer update(Customer customer);
    Customer findById(Integer id);
    Page<Customer> findAll(Pageable page);

}
