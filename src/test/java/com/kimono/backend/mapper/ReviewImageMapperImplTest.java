package com.kimono.backend.mapper;

import com.kimono.backend.domain.dto.ReviewImageDto;
import com.kimono.backend.domain.entities.ReviewEntity;
import com.kimono.backend.domain.entities.ReviewImageEntity;
import com.kimono.backend.mappers.impl.ReviewImageMapperImpl;
import com.kimono.backend.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ReviewImageMapperImplTest {

    private ReviewImageMapperImpl reviewImageMapper;
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        reviewRepository = Mockito.mock(ReviewRepository.class);
        reviewImageMapper = new ReviewImageMapperImpl(reviewRepository);
    }

    @Test
    void testMapToReviewImageDto() {
        // Given
        ReviewEntity review = ReviewEntity.builder().id(1).text("Great product!").build();

        ReviewImageEntity reviewImageEntity = ReviewImageEntity.builder()
                .id(2)
                .displayOrder(10)
                .review(review)
                .url("http://example.com/image.jpg")
                .build();

        // When
        ReviewImageDto reviewImageDto = reviewImageMapper.mapTo(reviewImageEntity);

        // Then
        assertNotNull(reviewImageDto);
        assertEquals(reviewImageEntity.getId(), reviewImageDto.getId());
        assertEquals(reviewImageEntity.getDisplayOrder(), reviewImageDto.getDisplayOrder());
        assertEquals(review.getId(), reviewImageDto.getReviewId());
        assertEquals(reviewImageEntity.getUrl(), reviewImageDto.getUrl());
    }

    @Test
    void testMapToReviewImageDtoWithNull() {
        // When
        ReviewImageDto reviewImageDto = reviewImageMapper.mapTo(null);

        // Then
        assertNull(reviewImageDto);
    }

    @Test
    void testMapFromReviewImageDto() {
        // Given
        ReviewImageDto reviewImageDto = ReviewImageDto.builder()
                .id(2)
                .displayOrder(10)
                .reviewId(1)
                .url("http://example.com/image.jpg")
                .build();

        ReviewEntity review = ReviewEntity.builder().id(1).text("Great product!").build();

        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        // When
        ReviewImageEntity reviewImageEntity = reviewImageMapper.mapFrom(reviewImageDto);

        // Then
        assertNotNull(reviewImageEntity);
        assertEquals(reviewImageDto.getId(), reviewImageEntity.getId());
        assertEquals(reviewImageDto.getDisplayOrder(), reviewImageEntity.getDisplayOrder());
        assertNotNull(reviewImageEntity.getReview());
        assertEquals(review.getId(), reviewImageEntity.getReview().getId());
        assertEquals(reviewImageDto.getUrl(), reviewImageEntity.getUrl());
    }

    @Test
    void testMapFromReviewImageDtoWithInvalidReview() {
        // Given
        ReviewImageDto reviewImageDto = ReviewImageDto.builder()
                .reviewId(999)
                .build();

        when(reviewRepository.findById(999)).thenReturn(Optional.empty());

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reviewImageMapper.mapFrom(reviewImageDto));
        assertEquals("Review not found with id: 999", exception.getMessage());
    }

    @Test
    void testMapFromReviewImageDtoWithNull() {
        // When
        ReviewImageEntity reviewImageEntity = reviewImageMapper.mapFrom(null);

        // Then
        assertNull(reviewImageEntity);
    }
}
