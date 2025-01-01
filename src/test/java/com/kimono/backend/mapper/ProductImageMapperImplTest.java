package com.kimono.backend.mapper;

import com.kimono.backend.domain.dto.ProductImageDto;
import com.kimono.backend.domain.entities.ProductEntity;
import com.kimono.backend.domain.entities.ProductImageEntity;
import com.kimono.backend.mappers.impl.ProductImageMapperImpl;
import com.kimono.backend.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProductImageMapperImplTest {

    private ProductImageMapperImpl productImageMapper;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productImageMapper = new ProductImageMapperImpl(productRepository);
    }

    @Test
    void testMapToProductImageDto() {
        // Given
        ProductEntity product = ProductEntity.builder().id(1).name("Product 1").build();

        ProductImageEntity productImageEntity = ProductImageEntity.builder()
                .id(2)
                .displayOrder(10)
                .product(product)
                .url("http://example.com/image.jpg")
                .build();

        // When
        ProductImageDto productImageDto = productImageMapper.mapTo(productImageEntity);

        // Then
        assertNotNull(productImageDto);
        assertEquals(productImageEntity.getId(), productImageDto.getId());
        assertEquals(productImageEntity.getDisplayOrder(), productImageDto.getDisplayOrder());
        assertEquals(product.getId(), productImageDto.getProductId());
        assertEquals(productImageEntity.getUrl(), productImageDto.getUrl());
    }

    @Test
    void testMapToProductImageDtoWithNull() {
        // When
        ProductImageDto productImageDto = productImageMapper.mapTo(null);

        // Then
        assertNull(productImageDto);
    }

    @Test
    void testMapFromProductImageDto() {
        // Given
        ProductImageDto productImageDto = ProductImageDto.builder()
                .id(2)
                .displayOrder(10)
                .productId(1)
                .url("http://example.com/image.jpg")
                .build();

        ProductEntity product = ProductEntity.builder().id(1).name("Product 1").build();

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // When
        ProductImageEntity productImageEntity = productImageMapper.mapFrom(productImageDto);

        // Then
        assertNotNull(productImageEntity);
        assertEquals(productImageDto.getId(), productImageEntity.getId());
        assertEquals(productImageDto.getDisplayOrder(), productImageEntity.getDisplayOrder());
        assertNotNull(productImageEntity.getProduct());
        assertEquals(product.getId(), productImageEntity.getProduct().getId());
        assertEquals(productImageDto.getUrl(), productImageEntity.getUrl());
    }

    @Test
    void testMapFromProductImageDtoWithInvalidProduct() {
        // Given
        ProductImageDto productImageDto = ProductImageDto.builder()
                .productId(999)
                .build();

        when(productRepository.findById(999)).thenReturn(Optional.empty());

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productImageMapper.mapFrom(productImageDto));
        assertEquals("Product not found with id: 999", exception.getMessage());
    }

    @Test
    void testMapFromProductImageDtoWithNull() {
        // When
        ProductImageEntity productImageEntity = productImageMapper.mapFrom(null);

        // Then
        assertNull(productImageEntity);
    }
}
