package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.SessionDto;
import com.kimono.backend.domain.entities.SessionEntity;
import com.kimono.backend.domain.entities.CustomerEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.repositories.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class SessionMapperImpl implements Mapper<SessionEntity, SessionDto> {

    private final CustomerRepository customerRepository;

    public SessionMapperImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public SessionDto mapTo(SessionEntity entity) {
        if (entity == null) {
            return null;
        }

        return SessionDto.builder()
                .id(entity.getId())
                .customerId(entity.getCustomer() != null ? entity.getCustomer().getId() : null)
                .token(entity.getToken())
                .expirationDate(entity.getExpirationDate())
                .build();
    }

    @Override
    public SessionEntity mapFrom(SessionDto dto) {
        if (dto == null) {
            return null;
        }

        CustomerEntity customer = null;
        if (dto.getCustomerId() != null) {
            customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + dto.getCustomerId()));
        }

        return SessionEntity.builder()
                .id(dto.getId())
                .customer(customer)
                .token(dto.getToken())
                .expirationDate(dto.getExpirationDate())
                .build();
    }
}
