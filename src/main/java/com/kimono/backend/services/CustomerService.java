package com.kimono.backend.services;

import com.kimono.backend.domain.entities.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    CustomerEntity save(CustomerEntity customerEntity);

    List<CustomerEntity> findAll();

    Optional<CustomerEntity> findById(Integer id);

    List<CustomerEntity> findByName(String name);

    void deleteCustomerById(Integer id);

    boolean doesNotExist(Integer id);

    CustomerEntity partialUpdate(Integer id, CustomerEntity customerEntity);

}
