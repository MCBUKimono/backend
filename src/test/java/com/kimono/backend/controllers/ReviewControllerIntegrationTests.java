package com.kimono.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.dto.ReviewDto;
import com.kimono.backend.domain.entities.ReviewEntity;
import com.kimono.backend.services.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ReviewControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    public ReviewControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateReviewSuccessfullyReturnsHttp201Created() throws Exception {
        ReviewDto testReviewDto = TestDataUtil.createTestReviewDto();

        String reviewRequestJson = objectMapper.writeValueAsString(testReviewDto);

        mockMvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reviewRequestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAllReviews() throws Exception {
        ReviewEntity reviewEntity = TestDataUtil.createTestReviewEntity();
        reviewService.save(reviewEntity);

        mockMvc.perform(get("/reviews")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].text").value(reviewEntity.getText()));
    }

    @Test
    public void testGetReviewById() throws Exception {
        ReviewEntity reviewEntity = TestDataUtil.createTestReviewEntity();
        ReviewEntity savedReview = reviewService.save(reviewEntity);

        mockMvc.perform(get("/reviews/" + savedReview.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedReview.getId()))
                .andExpect(jsonPath("$.text").value(savedReview.getText()));
    }

    @Test
    public void testUpdateReviewSuccessfully() throws Exception {
        ReviewEntity reviewEntity = TestDataUtil.createTestReviewEntity();
        ReviewEntity savedReview = reviewService.save(reviewEntity);

        ReviewDto updatedReviewDto = TestDataUtil.createTestReviewDto();
        updatedReviewDto.setText("Updated review text");
        updatedReviewDto.setScoreOutOf100(95);

        String updatedReviewJson = objectMapper.writeValueAsString(updatedReviewDto);

        mockMvc.perform(put("/reviews/" + savedReview.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedReviewJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Updated review text"))
                .andExpect(jsonPath("$.scoreOutOf100").value(95));
    }

    @Test
    public void testDeleteReviewById() throws Exception {
        ReviewEntity reviewEntity = TestDataUtil.createTestReviewEntity();
        ReviewEntity savedReview = reviewService.save(reviewEntity);

        mockMvc.perform(delete("/reviews/" + savedReview.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
