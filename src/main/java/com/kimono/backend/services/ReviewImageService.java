package com.kimono.backend.services;

import com.kimono.backend.domain.entities.ReviewImageEntity;

import java.util.List;
import java.util.Optional;

public interface ReviewImageService {
    ReviewImageEntity save(ReviewImageEntity reviewImageEntity);

    List<ReviewImageEntity> findAll();

    Optional<ReviewImageEntity> findById(Integer id);

    void deleteReviewImageById(Integer id);

    boolean doesNotExist(Integer id);

    ReviewImageEntity partialUpdate(Integer id, ReviewImageEntity reviewImageEntity);

}
