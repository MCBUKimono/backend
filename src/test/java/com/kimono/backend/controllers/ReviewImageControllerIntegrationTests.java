package com.kimono.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.dto.ReviewImageDto;
import com.kimono.backend.domain.entities.ReviewImageEntity;
import com.kimono.backend.services.ReviewImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ReviewImageControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    @Autowired
    private ReviewImageService reviewImageService;

    @Autowired
    public ReviewImageControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateReviewImageSuccessfullyReturnsHttp201Created() throws Exception {
        ReviewImageDto testReviewImageDto = TestDataUtil.createTestReviewImageDtoA();

        String reviewImageRequestJson = objectMapper.writeValueAsString(testReviewImageDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/review-image")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reviewImageRequestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetAllReviewImages() throws Exception {
        ReviewImageEntity reviewImageEntity = TestDataUtil.createTestReviewImageEntityA();
        reviewImageService.save(reviewImageEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/review-image")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].url").value(reviewImageEntity.getUrl())
        );
    }

    @Test
    public void testGetReviewImageById() throws Exception {
        ReviewImageEntity reviewImageEntity = TestDataUtil.createTestReviewImageEntityA();
        ReviewImageEntity savedReviewImage = reviewImageService.save(reviewImageEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/review-image/" + savedReviewImage.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.url").value(reviewImageEntity.getUrl())
        );
    }

    @Test
    public void testUpdateReviewImage() throws Exception {
        ReviewImageEntity testReviewImageEntity = TestDataUtil.createTestReviewImageEntityA();
        ReviewImageEntity savedReviewImage = reviewImageService.save(testReviewImageEntity);

        ReviewImageDto testReviewImageDto = TestDataUtil.createTestReviewImageDtoA();
        testReviewImageDto.setUrl("http://updated-review-image-url.com");
        String reviewImageDtoJson = objectMapper.writeValueAsString(testReviewImageDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/review-image/" + savedReviewImage.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reviewImageDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testPartialUpdateReviewImage() throws Exception {
        ReviewImageEntity testReviewImageEntity = TestDataUtil.createTestReviewImageEntityA();
        ReviewImageEntity savedReviewImage = reviewImageService.save(testReviewImageEntity);

        ReviewImageDto testReviewImageDto = new ReviewImageDto();
        testReviewImageDto.setUrl("http://partial-updated-review-image-url.com");
        String reviewImageDtoJson = objectMapper.writeValueAsString(testReviewImageDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/review-image/" + savedReviewImage.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reviewImageDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteReviewImageById() throws Exception {
        ReviewImageEntity testReviewImageEntity = TestDataUtil.createTestReviewImageEntityA();
        ReviewImageEntity savedReviewImage = reviewImageService.save(testReviewImageEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/review-image/" + savedReviewImage.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteReviewImageByIdWithNotFoundResponse() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/review-image/" + 9999)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
