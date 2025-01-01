package com.kimono.backend.mapper;

import com.kimono.backend.domain.dto.SessionDto;
import com.kimono.backend.domain.entities.CustomerEntity;
import com.kimono.backend.domain.entities.SessionEntity;
import com.kimono.backend.mappers.impl.SessionMapperImpl;
import com.kimono.backend.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class SessionMapperImplTest {

    private SessionMapperImpl sessionMapper;
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        sessionMapper = new SessionMapperImpl(customerRepository);
    }

    @Test
    void testMapToSessionDto() {
        // Given
        CustomerEntity customer = CustomerEntity.builder().id(1).name("John Doe").build();

        SessionEntity sessionEntity = SessionEntity.builder()
                .id(2)
                .customer(customer)
                .token("sample-token")
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();

        // When
        SessionDto sessionDto = sessionMapper.mapTo(sessionEntity);

        // Then
        assertNotNull(sessionDto);
        assertEquals(sessionEntity.getId(), sessionDto.getId());
        assertEquals(customer.getId(), sessionDto.getCustomerId());
        assertEquals(sessionEntity.getToken(), sessionDto.getToken());
        assertEquals(sessionEntity.getExpirationDate(), sessionDto.getExpirationDate());
    }

    @Test
    void testMapToSessionDtoWithNull() {
        // When
        SessionDto sessionDto = sessionMapper.mapTo(null);

        // Then
        assertNull(sessionDto);
    }

    @Test
    void testMapFromSessionDto() {
        // Given
        SessionDto sessionDto = SessionDto.builder()
                .id(2)
                .customerId(1)
                .token("sample-token")
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();

        CustomerEntity customer = CustomerEntity.builder().id(1).name("John Doe").build();

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        // When
        SessionEntity sessionEntity = sessionMapper.mapFrom(sessionDto);

        // Then
        assertNotNull(sessionEntity);
        assertEquals(sessionDto.getId(), sessionEntity.getId());
        assertNotNull(sessionEntity.getCustomer());
        assertEquals(customer.getId(), sessionEntity.getCustomer().getId());
        assertEquals(sessionDto.getToken(), sessionEntity.getToken());
        assertEquals(sessionDto.getExpirationDate(), sessionEntity.getExpirationDate());
    }

    @Test
    void testMapFromSessionDtoWithInvalidCustomer() {
        // Given
        SessionDto sessionDto = SessionDto.builder()
                .customerId(999)
                .build();

        when(customerRepository.findById(999)).thenReturn(Optional.empty());

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> sessionMapper.mapFrom(sessionDto));
        assertEquals("Customer not found with id: 999", exception.getMessage());
    }

    @Test
    void testMapFromSessionDtoWithNull() {
        // When
        SessionEntity sessionEntity = sessionMapper.mapFrom(null);

        // Then
        assertNull(sessionEntity);
    }
}
