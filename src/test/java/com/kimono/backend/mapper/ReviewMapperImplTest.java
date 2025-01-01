package com.kimono.backend.mapper;

import com.kimono.backend.domain.dto.ReviewDto;
import com.kimono.backend.domain.entities.CustomerEntity;
import com.kimono.backend.domain.entities.ProductEntity;
import com.kimono.backend.domain.entities.ReviewEntity;
import com.kimono.backend.domain.entities.ReviewImageEntity;
import com.kimono.backend.mappers.impl.ReviewMapperImpl;
import com.kimono.backend.repositories.CustomerRepository;
import com.kimono.backend.repositories.ProductRepository;
import com.kimono.backend.repositories.ReviewImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ReviewMapperImplTest {

    private ReviewMapperImpl reviewMapper;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private ReviewImageRepository reviewImageRepository;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        productRepository = Mockito.mock(ProductRepository.class);
        reviewImageRepository = Mockito.mock(ReviewImageRepository.class);
        reviewMapper = new ReviewMapperImpl(customerRepository, productRepository, reviewImageRepository);
    }

    @Test
    void testMapToReviewDto() {
        // Given
        CustomerEntity customer = CustomerEntity.builder().id(1).name("John Doe").build();
        ProductEntity product = ProductEntity.builder().id(2).name("Product 1").build();
        ReviewImageEntity image = ReviewImageEntity.builder().id(3).url("http://example.com/image.jpg").build();

        ReviewEntity reviewEntity = ReviewEntity.builder()
                .id(4)
                .product(product)
                .customer(customer)
                .images(List.of(image))
                .text("Great product!")
                .scoreOutOf100(95)
                .build();

        // When
        ReviewDto reviewDto = reviewMapper.mapTo(reviewEntity);

        // Then
        assertNotNull(reviewDto);
        assertEquals(reviewEntity.getId(), reviewDto.getId());
        assertEquals(product.getId(), reviewDto.getProductId());
        assertEquals(customer.getId(), reviewDto.getCustomerId());
        assertEquals(1, reviewDto.getImageIds().size());
        assertTrue(reviewDto.getImageIds().contains(image.getId()));
        assertEquals(reviewEntity.getText(), reviewDto.getText());
        assertEquals(reviewEntity.getScoreOutOf100(), reviewDto.getScoreOutOf100());
    }

    @Test
    void testMapToReviewDtoWithNull() {
        // When
        ReviewDto reviewDto = reviewMapper.mapTo(null);

        // Then
        assertNull(reviewDto);
    }

    @Test
    void testMapFromReviewDto() {
        // Given
        ReviewDto reviewDto = ReviewDto.builder()
                .id(4)
                .productId(2)
                .customerId(1)
                .imageIds(List.of(3))
                .text("Great product!")
                .scoreOutOf100(95)
                .build();

        CustomerEntity customer = CustomerEntity.builder().id(1).name("John Doe").build();
        ProductEntity product = ProductEntity.builder().id(2).name("Product 1").build();
        ReviewImageEntity image = ReviewImageEntity.builder().id(3).url("http://example.com/image.jpg").build();

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(productRepository.findById(2)).thenReturn(Optional.of(product));
        when(reviewImageRepository.findById(3)).thenReturn(Optional.of(image));

        // When
        ReviewEntity reviewEntity = reviewMapper.mapFrom(reviewDto);

        // Then
        assertNotNull(reviewEntity);
        assertEquals(reviewDto.getId(), reviewEntity.getId());
        assertNotNull(reviewEntity.getProduct());
        assertEquals(product.getId(), reviewEntity.getProduct().getId());
        assertNotNull(reviewEntity.getCustomer());
        assertEquals(customer.getId(), reviewEntity.getCustomer().getId());
        assertEquals(1, reviewEntity.getImages().size());
        assertTrue(reviewEntity.getImages().contains(image));
        assertEquals(reviewDto.getText(), reviewEntity.getText());
        assertEquals(reviewDto.getScoreOutOf100(), reviewEntity.getScoreOutOf100());
    }

    @Test
    void testMapFromReviewDtoWithInvalidCustomer() {
        // Given
        ReviewDto reviewDto = ReviewDto.builder()
                .customerId(999)
                .build();

        when(customerRepository.findById(999)).thenReturn(Optional.empty());

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reviewMapper.mapFrom(reviewDto));
        assertEquals("Customer not found with id: 999", exception.getMessage());
    }

    @Test
    void testMapFromReviewDtoWithInvalidProduct() {
        // Given
        ReviewDto reviewDto = ReviewDto.builder()
                .productId(999)
                .build();

        when(productRepository.findById(999)).thenReturn(Optional.empty());

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reviewMapper.mapFrom(reviewDto));
        assertEquals("Product not found with id: 999", exception.getMessage());
    }

    @Test
    void testMapFromReviewDtoWithInvalidImage() {
        // Given
        ReviewDto reviewDto = ReviewDto.builder()
                .imageIds(List.of(999))
                .build();

        when(reviewImageRepository.findById(999)).thenReturn(Optional.empty());

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reviewMapper.mapFrom(reviewDto));
        assertEquals("Review image not found with id: 999", exception.getMessage());
    }

    @Test
    void testMapFromReviewDtoWithNull() {
        // When
        ReviewEntity reviewEntity = reviewMapper.mapFrom(null);

        // Then
        assertNull(reviewEntity);
    }
}
