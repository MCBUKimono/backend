package com.kimono.backend.services.impl;

import com.kimono.backend.domain.entities.CustomerEntity;
import com.kimono.backend.repositories.CustomerRepository;
import com.kimono.backend.services.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerEntity save(CustomerEntity customerEntity) {
        return customerRepository.save(customerEntity);
    }

    @Override
    public List<CustomerEntity> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<CustomerEntity> findById(Integer id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<CustomerEntity> findByName(String name) {
        return customerRepository.findByName(name);
    }

    @Override
    public void deleteCustomerById(Integer id) {
        if(doesNotExist(id)){
            throw new EntityNotFoundException();
        }
        customerRepository.deleteById(id);
    }

    @Override
    public boolean doesNotExist(Integer id) {
        return !customerRepository.existsById(id);
    }

    @Override
    public CustomerEntity partialUpdate(Integer id, CustomerEntity customerEntity) {
        return customerRepository.findById(id).map(existingProduct -> {
            Optional.ofNullable(customerEntity.getName()).ifPresent(existingProduct::setName);
            Optional.ofNullable(customerEntity.getPasswordHash()).ifPresent(existingProduct::setPasswordHash);
            Optional.ofNullable(customerEntity.getFavoriteProducts()).ifPresent(existingProduct::setFavoriteProducts);
            return customerRepository.save(existingProduct);
        }).orElseThrow(() -> new RuntimeException("Product does not exist"));
    }
}
