package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.OrderDto;
import com.kimono.backend.domain.entities.OrderEntity;
import com.kimono.backend.domain.entities.CustomerEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.repositories.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderMapperImpl implements Mapper<OrderEntity, OrderDto> {

    private final CustomerRepository customerRepository;

    public OrderMapperImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public OrderDto mapTo(OrderEntity entity) {
        if (entity == null) {
            return null;
        }

        return OrderDto.builder()
                .id(entity.getId())
                .shippingAddress(entity.getShippingAddress())
                .shippingName(entity.getShippingName())
                .trackingCode(entity.getTrackingCode())
                .status(entity.getStatus())
                .customerId(entity.getCustomer() != null ? entity.getCustomer().getId() : null)
                .build();
    }

    @Override
    public OrderEntity mapFrom(OrderDto dto) {
        if (dto == null) {
            return null;
        }

        CustomerEntity customer = null;
        if (dto.getCustomerId() != null) {
            customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + dto.getCustomerId()));
        }

        return OrderEntity.builder()
                .id(dto.getId())
                .shippingAddress(dto.getShippingAddress())
                .shippingName(dto.getShippingName())
                .trackingCode(dto.getTrackingCode())
                .status(dto.getStatus())
                .customer(customer)
                .build();
    }
}
