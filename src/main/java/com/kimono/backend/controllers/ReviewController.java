package com.kimono.backend.controllers;

import com.kimono.backend.domain.dto.ReviewDto;
import com.kimono.backend.domain.entities.ReviewEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final Mapper<ReviewEntity, ReviewDto> mapper;

    @Autowired
    public ReviewController(ReviewService reviewService, Mapper<ReviewEntity, ReviewDto> mapper) {
        this.reviewService = reviewService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
        ReviewEntity reviewEntity = mapper.mapFrom(reviewDto);
        ReviewEntity savedReview = reviewService.save(reviewEntity);
        return new ResponseEntity<>(mapper.mapTo(savedReview), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        List<ReviewEntity> reviews = reviewService.findAll();
        List<ReviewDto> reviewDtos = reviews.stream().map(mapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(reviewDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Integer id) {
        ReviewEntity review = reviewService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
        return new ResponseEntity<>(mapper.mapTo(review), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Integer id, @RequestBody ReviewDto reviewDto) {
        if (reviewService.doesNotExist(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }

        reviewDto.setId(id);
        ReviewEntity updatedReview = reviewService.save(mapper.mapFrom(reviewDto));
        return new ResponseEntity<>(mapper.mapTo(updatedReview), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReviewById(@PathVariable Integer id) {
        try {
            reviewService.deleteReviewById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
