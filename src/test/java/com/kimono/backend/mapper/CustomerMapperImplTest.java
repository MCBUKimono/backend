package com.kimono.backend.mapper;

import com.kimono.backend.domain.dto.CustomerDto;
import com.kimono.backend.domain.entities.CustomerEntity;
import com.kimono.backend.domain.entities.ProductEntity;
import com.kimono.backend.mappers.impl.CustomerMapperImpl;
import com.kimono.backend.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CustomerMapperImplTest {

    private CustomerMapperImpl customerMapper;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        customerMapper = new CustomerMapperImpl(productRepository);
    }

    @Test
    void testMapToCustomerDto() {
        // Given
        ProductEntity favoriteProduct = ProductEntity.builder().id(1).name("Product 1").build();

        CustomerEntity customerEntity = CustomerEntity.builder()
                .id(1)
                .name("John Doe")
                .passwordHash("hashedpassword")
                .favoriteProducts(List.of(favoriteProduct))
                .build();

        // When
        CustomerDto customerDto = customerMapper.mapTo(customerEntity);

        // Then
        assertNotNull(customerDto);
        assertEquals(customerEntity.getId(), customerDto.getId());
        assertEquals(customerEntity.getName(), customerDto.getName());
        assertEquals(customerEntity.getPasswordHash(), customerDto.getPasswordHash());
        assertEquals(1, customerDto.getFavoriteProductIds().size());
        assertTrue(customerDto.getFavoriteProductIds().contains(favoriteProduct.getId()));
    }

    @Test
    void testMapToCustomerDtoWithNull() {
        // When
        CustomerDto customerDto = customerMapper.mapTo(null);

        // Then
        assertNull(customerDto);
    }

    @Test
    void testMapFromCustomerDto() {
        // Given
        CustomerDto customerDto = CustomerDto.builder()
                .id(1)
                .name("John Doe")
                .passwordHash("hashedpassword")
                .favoriteProductIds(List.of(1))
                .build();

        ProductEntity favoriteProduct = ProductEntity.builder().id(1).name("Product 1").build();

        when(productRepository.findById(1)).thenReturn(Optional.of(favoriteProduct));

        // When
        CustomerEntity customerEntity = customerMapper.mapFrom(customerDto);

        // Then
        assertNotNull(customerEntity);
        assertEquals(customerDto.getId(), customerEntity.getId());
        assertEquals(customerDto.getName(), customerEntity.getName());
        assertEquals(customerDto.getPasswordHash(), customerEntity.getPasswordHash());
        assertEquals(1, customerEntity.getFavoriteProducts().size());
        assertTrue(customerEntity.getFavoriteProducts().contains(favoriteProduct));
    }

    @Test
    void testMapFromCustomerDtoWithInvalidProduct() {
        // Given
        CustomerDto customerDto = CustomerDto.builder()
                .favoriteProductIds(List.of(999))
                .build();

        when(productRepository.findById(999)).thenReturn(Optional.empty());

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> customerMapper.mapFrom(customerDto));
        assertEquals("Product not found with id: 999", exception.getMessage());
    }

    @Test
    void testMapFromCustomerDtoWithNull() {
        // When
        CustomerEntity customerEntity = customerMapper.mapFrom(null);

        // Then
        assertNull(customerEntity);
    }
}
