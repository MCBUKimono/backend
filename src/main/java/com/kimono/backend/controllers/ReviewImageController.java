package com.kimono.backend.controllers;

import com.kimono.backend.domain.dto.ReviewImageDto;
import com.kimono.backend.domain.entities.ReviewImageEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.services.ReviewImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/review-image")
public class ReviewImageController {

    private final ReviewImageService reviewImageService;
    private final Mapper<ReviewImageEntity, ReviewImageDto> mapper;

    @Autowired
    public ReviewImageController(ReviewImageService reviewImageService, Mapper<ReviewImageEntity, ReviewImageDto> mapper) {
        this.reviewImageService = reviewImageService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ReviewImageDto> createReviewImage(@RequestBody ReviewImageDto reviewImageDto) {
        reviewImageDto.setId(null);
        ReviewImageEntity reviewImageEntity = mapper.mapFrom(reviewImageDto);
        ReviewImageEntity savedReviewImage = reviewImageService.save(reviewImageEntity);
        return new ResponseEntity<>(mapper.mapTo(savedReviewImage), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReviewImageDto>> getAllReviewImages() {
        List<ReviewImageEntity> reviewImages = reviewImageService.findAll();
        List<ReviewImageDto> reviewImageDtos = reviewImages.stream().map(mapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(reviewImageDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewImageDto> getReviewImageById(@PathVariable Integer id) {
        ReviewImageEntity reviewImage = reviewImageService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Image not found"));
        return new ResponseEntity<>(mapper.mapTo(reviewImage), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewImageDto> updateReviewImage(@PathVariable Integer id, @RequestBody ReviewImageDto reviewImageDto) {
        if (reviewImageService.doesNotExist(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Image not found");
        }

        reviewImageDto.setId(id);
        ReviewImageEntity updatedReviewImage = reviewImageService.save(mapper.mapFrom(reviewImageDto));
        return new ResponseEntity<>(mapper.mapTo(updatedReviewImage), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReviewImageDto> partiallyUpdateReviewImage(@PathVariable Integer id, @RequestBody ReviewImageDto reviewImageDto) {
        if (reviewImageService.doesNotExist(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Image not found");
        }

        ReviewImageEntity updatedReviewImage = reviewImageService.partialUpdate(id, mapper.mapFrom(reviewImageDto));
        return new ResponseEntity<>(mapper.mapTo(updatedReviewImage), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReviewImageById(@PathVariable Integer id) {
        try {
            reviewImageService.deleteReviewImageById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
