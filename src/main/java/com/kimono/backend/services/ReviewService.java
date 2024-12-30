package com.kimono.backend.services;

import com.kimono.backend.domain.entities.ReviewEntity;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    ReviewEntity save(ReviewEntity review);
    List<ReviewEntity> findAll();
    Optional<ReviewEntity> findById(Integer id);
    void deleteReviewById(Integer id);
    boolean doesNotExist(Integer id);
}
