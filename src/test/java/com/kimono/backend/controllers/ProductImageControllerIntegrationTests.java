package com.kimono.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.dto.ProductImageDto;
import com.kimono.backend.domain.entities.ProductImageEntity;
import com.kimono.backend.services.ProductImageService;
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
public class ProductImageControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    public ProductImageControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateProductImageSuccessfullyReturnsHttp201Created() throws Exception {
        ProductImageDto testProductImageDto = TestDataUtil.createTestProductImageDtoA();

        String productImageRequestJson = objectMapper.writeValueAsString(testProductImageDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/product-image")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productImageRequestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetAllProductImages() throws Exception {
        ProductImageEntity productImageEntity = TestDataUtil.createTestProductImageEntityA();
        productImageService.save(productImageEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/product-image")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].url").value(productImageEntity.getUrl())
        );
    }

    @Test
    public void testGetProductImageById() throws Exception {
        ProductImageEntity productImageEntity = TestDataUtil.createTestProductImageEntityA();
        ProductImageEntity savedProductImage = productImageService.save(productImageEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/product-image/" + savedProductImage.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.url").value(productImageEntity.getUrl())
        );
    }

    @Test
    public void testUpdateProductImage() throws Exception {
        ProductImageEntity testProductImageEntity = TestDataUtil.createTestProductImageEntityA();
        ProductImageEntity savedProductImage = productImageService.save(testProductImageEntity);

        ProductImageDto testProductImageDto = TestDataUtil.createTestProductImageDtoA();
        testProductImageDto.setUrl("http://updated-image-url.com");
        String productImageDtoJson = objectMapper.writeValueAsString(testProductImageDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/product-image/" + savedProductImage.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productImageDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testPartialUpdateProductImage() throws Exception {
        ProductImageEntity testProductImageEntity = TestDataUtil.createTestProductImageEntityA();
        ProductImageEntity savedProductImage = productImageService.save(testProductImageEntity);

        ProductImageDto testProductImageDto = new ProductImageDto();
        testProductImageDto.setUrl("http://partial-updated-image-url.com");
        String productImageDtoJson = objectMapper.writeValueAsString(testProductImageDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/product-image/" + savedProductImage.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productImageDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteProductImageById() throws Exception {
        ProductImageEntity testProductImageEntity = TestDataUtil.createTestProductImageEntityA();
        ProductImageEntity savedProductImage = productImageService.save(testProductImageEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/product-image/" + savedProductImage.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteProductImageByIdWithNotFoundResponse() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/product-image/" + 9999)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
