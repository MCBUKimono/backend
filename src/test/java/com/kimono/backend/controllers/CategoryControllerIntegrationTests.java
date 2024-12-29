package com.kimono.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.dto.CategoryDto;
import com.kimono.backend.domain.entities.CategoryEntity;
import com.kimono.backend.services.CategoryService;
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
public class CategoryControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    public CategoryControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateCategorySuccessfullyReturnsHttp201Created() throws Exception {
        CategoryDto testCategoryDto = TestDataUtil.createTestCategoryDtoA();

        String categoryRequestJson = objectMapper.writeValueAsString(testCategoryDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryRequestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetAllCategories() throws Exception {
        CategoryEntity categoryEntity = TestDataUtil.createTestCategoryEntityA();
        categoryService.save(categoryEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/category")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(categoryEntity.getName())
        );
    }

    @Test
    public void testGetCategoryById() throws Exception {
        CategoryEntity categoryEntity = TestDataUtil.createTestCategoryEntityA();
        CategoryEntity savedCategoryEntity = categoryService.save(categoryEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/category/" + savedCategoryEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(categoryEntity.getName())
        );
    }

    @Test
    public void testUpdateCategory() throws Exception {
        CategoryEntity testCategoryEntity = TestDataUtil.createTestCategoryEntityA();
        CategoryEntity savedCategory = categoryService.save(testCategoryEntity);

        CategoryDto testCategoryDto = TestDataUtil.createTestCategoryDtoA();
        testCategoryDto.setName("UpdatedCategory");
        String categoryDtoJson = objectMapper.writeValueAsString(testCategoryDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/category/" + savedCategory.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testPartialUpdateCategory() throws Exception {
        CategoryEntity testCategoryEntity = TestDataUtil.createTestCategoryEntityA();
        CategoryEntity savedCategory = categoryService.save(testCategoryEntity);

        CategoryDto testCategoryDto = TestDataUtil.createTestCategoryDtoA();
        testCategoryDto.setName("PartiallyUpdatedCategory");
        String categoryDtoJson = objectMapper.writeValueAsString(testCategoryDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/category/" + savedCategory.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteCategoryById() throws Exception {
        CategoryEntity testCategoryEntity = TestDataUtil.createTestCategoryEntityA();
        CategoryEntity savedCategory = categoryService.save(testCategoryEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/category/" + savedCategory.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteCategoryByIdWithNotFoundResponse() throws Exception {
        CategoryEntity testCategoryEntity = TestDataUtil.createTestCategoryEntityA();
        CategoryEntity savedCategory = categoryService.save(testCategoryEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/category/" + (savedCategory.getId() + 1))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
