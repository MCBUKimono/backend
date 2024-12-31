package com.kimono.backend.mappers.impl;

import com.kimono.backend.domain.dto.ReviewImageDto;
import com.kimono.backend.domain.entities.ReviewImageEntity;
import com.kimono.backend.domain.entities.ReviewEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.repositories.ReviewRepository;
import org.springframework.stereotype.Component;

@Component
public class ReviewImageMapperImpl implements Mapper<ReviewImageEntity, ReviewImageDto> {

    private final ReviewRepository reviewRepository;

    public ReviewImageMapperImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ReviewImageDto mapTo(ReviewImageEntity entity) {
        if (entity == null) {
            return null;
        }

        return ReviewImageDto.builder()
                .id(entity.getId())
                .displayOrder(entity.getDisplayOrder())
                .reviewId(entity.getReview() != null ? entity.getReview().getId() : null)
                .url(entity.getUrl())
                .build();
    }

    @Override
    public ReviewImageEntity mapFrom(ReviewImageDto dto) {
        if (dto == null) {
            return null;
        }

        ReviewEntity review = null;
        if (dto.getReviewId() != null) {
            review = reviewRepository.findById(dto.getReviewId())
                    .orElseThrow(() -> new IllegalArgumentException("Review not found with id: " + dto.getReviewId()));
        }

        return ReviewImageEntity.builder()
                .id(dto.getId())
                .displayOrder(dto.getDisplayOrder())
                .review(review)
                .url(dto.getUrl())
                .build();
    }
}
