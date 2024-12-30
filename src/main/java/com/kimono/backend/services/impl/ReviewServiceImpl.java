package com.kimono.backend.services.impl;

import com.kimono.backend.domain.entities.ReviewEntity;
import com.kimono.backend.repositories.ReviewRepository;
import com.kimono.backend.services.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ReviewEntity save(ReviewEntity review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<ReviewEntity> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<ReviewEntity> findById(Integer id) {
        return reviewRepository.findById(id);
    }

    @Override
    public void deleteReviewById(Integer id) {
        if (doesNotExist(id)) {
            throw new EntityNotFoundException("Review not found");
        }
        reviewRepository.deleteById(id);
    }

    @Override
    public boolean doesNotExist(Integer id) {
        return !reviewRepository.existsById(id);
    }
}
