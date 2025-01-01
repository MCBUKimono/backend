package com.kimono.backend.repositories;

import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.entities.ReviewEntity;
import com.kimono.backend.domain.entities.ReviewImageEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReviewImageRepositoryIntegrationTests {

    private final ReviewImageRepository underTest;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewImageRepositoryIntegrationTests(ReviewImageRepository underTest, ReviewRepository reviewRepository) {
        this.underTest = underTest;
        this.reviewRepository = reviewRepository;
    }

    @Test
    @Transactional
    public void testThatReviewImageCanBeCreatedAndRecalled() {
        ReviewEntity review = TestDataUtil.createTestReviewEntity();
        reviewRepository.save(review);

        ReviewImageEntity reviewImage = TestDataUtil.createTestReviewImageEntityA();
        reviewImage.setReview(review);
        underTest.save(reviewImage);

        Optional<ReviewImageEntity> result = underTest.findById(reviewImage.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(reviewImage);
    }

    @Test
    @Transactional
    public void testThatMultipleReviewImagesCanBeCreatedAndRecalled() {
        ReviewEntity review = TestDataUtil.createTestReviewEntity();
        reviewRepository.save(review);

        ReviewImageEntity reviewImage1 = TestDataUtil.createTestReviewImageEntityA();
        reviewImage1.setReview(review);
        underTest.save(reviewImage1);

        ReviewImageEntity reviewImage2 = TestDataUtil.createTestReviewImageEntityB();
        reviewImage2.setReview(review);
        underTest.save(reviewImage2);

        List<ReviewImageEntity> result = underTest.findAll();
        assertThat(result).hasSize(2).containsExactlyInAnyOrder(reviewImage1, reviewImage2);
    }

    @Test
    @Transactional
    public void testThatReviewImageCanBeUpdated() {
        ReviewEntity review = TestDataUtil.createTestReviewEntity();
        reviewRepository.save(review);

        ReviewImageEntity reviewImage = TestDataUtil.createTestReviewImageEntityA();
        reviewImage.setReview(review);
        underTest.save(reviewImage);

        reviewImage.setUrl("http://updated-url.com");
        underTest.save(reviewImage);

        Optional<ReviewImageEntity> result = underTest.findById(reviewImage.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(reviewImage);
    }

    @Test
    @Transactional
    public void testThatReviewImageCanBeDeleted() {
        ReviewEntity review = TestDataUtil.createTestReviewEntity();
        reviewRepository.save(review);

        ReviewImageEntity reviewImage = TestDataUtil.createTestReviewImageEntityA();
        reviewImage.setReview(review);
        underTest.save(reviewImage);

        underTest.deleteById(reviewImage.getId());
        Optional<ReviewImageEntity> result = underTest.findById(reviewImage.getId());
        assertThat(result).isEmpty();
    }

    @Test
    @Transactional
    public void testThatGetReviewImagesByReviewId() {
        ReviewEntity review = TestDataUtil.createTestReviewEntity();
        reviewRepository.save(review);

        ReviewImageEntity reviewImage = TestDataUtil.createTestReviewImageEntityA();
        reviewImage.setReview(review);
        underTest.save(reviewImage);

        List<ReviewImageEntity> result = underTest.findByReviewId(review.getId());
        assertThat(result).hasSize(1).containsExactly(reviewImage);
    }
}
