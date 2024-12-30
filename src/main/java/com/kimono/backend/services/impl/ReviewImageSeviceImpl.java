package com.kimono.backend.services.impl;

import com.kimono.backend.domain.entities.ReviewImageEntity;
import com.kimono.backend.repositories.ReviewImageRepository;
import com.kimono.backend.services.ReviewImageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewImageSeviceImpl implements ReviewImageService {
    private final ReviewImageRepository reviewImageRepository;

    public ReviewImageSeviceImpl(ReviewImageRepository reviewImageRepository) {
        this.reviewImageRepository = reviewImageRepository;
    }

    @Override
    public ReviewImageEntity save(ReviewImageEntity reviewImageEntity) {
        return reviewImageRepository.save(reviewImageEntity);
    }

    @Override
    public List<ReviewImageEntity> findAll() {
        return reviewImageRepository.findAll();
    }

    @Override
    public Optional<ReviewImageEntity> findById(Integer id) {
        return reviewImageRepository.findById(id);
    }

    @Override
    public void deleteReviewImageById(Integer id) {
        if(doesNotExist(id)){
            throw new EntityNotFoundException();
        }
        reviewImageRepository.deleteById(id);
    }

    @Override
    public boolean doesNotExist(Integer id) {
        return !reviewImageRepository.existsById(id);
    }

    @Override
    public ReviewImageEntity partialUpdate(Integer id, ReviewImageEntity reviewImageEntity) {
        return reviewImageRepository.findById(id).map(existing -> {
            Optional.ofNullable(reviewImageEntity.getUrl()).ifPresent(existing::setUrl);
            Optional.ofNullable(reviewImageEntity.getReview()).ifPresent(existing::setReview);
            Optional.ofNullable(reviewImageEntity.getDisplayOrder()).ifPresent(existing::setDisplayOrder);
            return reviewImageRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Review image does not exist"));
    }
}
