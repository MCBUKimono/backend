package com.kimono.backend.mapper;

import com.kimono.backend.domain.dto.OrderDto;
import com.kimono.backend.domain.entities.CustomerEntity;
import com.kimono.backend.domain.entities.OrderEntity;
import com.kimono.backend.domain.enums.OrderStatus;
import com.kimono.backend.mappers.impl.OrderMapperImpl;
import com.kimono.backend.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class OrderMapperImplTest {

    private OrderMapperImpl orderMapper;
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        orderMapper = new OrderMapperImpl(customerRepository);
    }

    @Test
    void testMapToOrderDto() {
        // Given
        CustomerEntity customer = CustomerEntity.builder().id(1).name("John Doe").build();

        OrderEntity orderEntity = OrderEntity.builder()
                .id(2)
                .shippingAddress("123 Main St")
                .shippingName("John Doe")
                .trackingCode("TRACK123")
                .status(OrderStatus.SHIPPED)
                .customer(customer)
                .build();

        // When
        OrderDto orderDto = orderMapper.mapTo(orderEntity);

        // Then
        assertNotNull(orderDto);
        assertEquals(orderEntity.getId(), orderDto.getId());
        assertEquals(orderEntity.getShippingAddress(), orderDto.getShippingAddress());
        assertEquals(orderEntity.getShippingName(), orderDto.getShippingName());
        assertEquals(orderEntity.getTrackingCode(), orderDto.getTrackingCode());
        assertEquals(orderEntity.getStatus(), orderDto.getStatus());
        assertEquals(customer.getId(), orderDto.getCustomerId());
    }

    @Test
    void testMapToOrderDtoWithNull() {
        // When
        OrderDto orderDto = orderMapper.mapTo(null);

        // Then
        assertNull(orderDto);
    }

    @Test
    void testMapFromOrderDto() {
        // Given
        OrderDto orderDto = OrderDto.builder()
                .id(2)
                .shippingAddress("123 Main St")
                .shippingName("John Doe")
                .trackingCode("TRACK123")
                .status(OrderStatus.SHIPPED)
                .customerId(1)
                .build();

        CustomerEntity customer = CustomerEntity.builder().id(1).name("John Doe").build();

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        // When
        OrderEntity orderEntity = orderMapper.mapFrom(orderDto);

        // Then
        assertNotNull(orderEntity);
        assertEquals(orderDto.getId(), orderEntity.getId());
        assertEquals(orderDto.getShippingAddress(), orderEntity.getShippingAddress());
        assertEquals(orderDto.getShippingName(), orderEntity.getShippingName());
        assertEquals(orderDto.getTrackingCode(), orderEntity.getTrackingCode());
        assertEquals(orderDto.getStatus(), orderEntity.getStatus());
        assertNotNull(orderEntity.getCustomer());
        assertEquals(customer.getId(), orderEntity.getCustomer().getId());
    }

    @Test
    void testMapFromOrderDtoWithInvalidCustomer() {
        // Given
        OrderDto orderDto = OrderDto.builder()
                .customerId(999)
                .build();

        when(customerRepository.findById(999)).thenReturn(Optional.empty());

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderMapper.mapFrom(orderDto));
        assertEquals("Customer not found with id: 999", exception.getMessage());
    }

    @Test
    void testMapFromOrderDtoWithNull() {
        // When
        OrderEntity orderEntity = orderMapper.mapFrom(null);

        // Then
        assertNull(orderEntity);
    }
}
