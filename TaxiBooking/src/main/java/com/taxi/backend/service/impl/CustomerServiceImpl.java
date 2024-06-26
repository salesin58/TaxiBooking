package com.taxi.backend.service.impl;

import com.taxi.backend.entities.Customer;
import com.taxi.backend.repository.CustomerRepository;
import com.taxi.backend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
      return null;
    }

    @Override
    public Customer findById(Integer id) {
        Optional<Customer> result = customerRepository.findById(id);

        Customer customer = null;

        if (result.isPresent()) {
            customer = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find customer id - " + id);
        }

        return customer;
    }

    @Override
    public Page<Customer> findAll(Pageable page) {
        return customerRepository.findAll(page);
    }


}
