package com.kimono.backend.controllers;

import com.kimono.backend.domain.dto.CustomerDto;
import com.kimono.backend.domain.entities.CustomerEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final Mapper<CustomerEntity, CustomerDto> mapper;

    @Autowired
    public CustomerController(CustomerService customerService, Mapper<CustomerEntity, CustomerDto> mapper) {
        this.customerService = customerService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        CustomerEntity customerEntity = mapper.mapFrom(customerDto);
        CustomerEntity savedCustomer = customerService.save(customerEntity);
        return new ResponseEntity<>(mapper.mapTo(savedCustomer), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerEntity> customers = customerService.findAll();
        List<CustomerDto> customerDtos = customers.stream().map(mapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(customerDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Integer id) {
        CustomerEntity customer = customerService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        return new ResponseEntity<>(mapper.mapTo(customer), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Integer id, @RequestBody CustomerDto customerDto) {
        if (customerService.doesNotExist(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }

        customerDto.setId(id);
        CustomerEntity updatedCustomer = customerService.save(mapper.mapFrom(customerDto));
        return new ResponseEntity<>(mapper.mapTo(updatedCustomer), HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDto> patchCustomer(@PathVariable Integer id, @RequestBody CustomerDto customerDto) {
        if (customerService.doesNotExist(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        CustomerEntity updatedCustomer = customerService.partialUpdate(id, mapper.mapFrom(customerDto));
        return new ResponseEntity<>(mapper.mapTo(updatedCustomer), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Integer id) {
        try {
            customerService.deleteCustomerById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
