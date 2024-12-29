package com.kimono.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.dto.BrandDto;
import com.kimono.backend.domain.entities.BrandEntity;
import com.kimono.backend.services.BrandService;
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
public class BrandControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    @Autowired
    private BrandService brandService;

    @Autowired
    public BrandControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateBrandSuccessfullyReturnsHttp201Created() throws Exception {
        BrandDto testBrandDtoA = TestDataUtil.createTestBrandDtoA();

        String brandRequestJson = objectMapper.writeValueAsString(testBrandDtoA);

        mockMvc.perform(MockMvcRequestBuilders.post("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandRequestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetAllBrands() throws Exception {
        BrandEntity brandEntity = TestDataUtil.createTestBrandA();
        brandService.save(brandEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(brandEntity.getName())
        );
    }

    @Test
    public void testGetBrandById() throws Exception {
        BrandEntity brandEntity = TestDataUtil.createTestBrandA();
        BrandEntity savedBrandEntity = brandService.save(brandEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/brand/" + savedBrandEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(brandEntity.getName())
        );
    }

    @Test
    public void testGetBrandsByName() throws Exception {
        BrandEntity brandEntity1 = TestDataUtil.createTestBrandA();
        BrandEntity brandEntity2 = TestDataUtil.createTestBrandA();
        brandService.save(brandEntity1);
        brandService.save(brandEntity2);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/brand/name/" + brandEntity1.getName())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(brandEntity1.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].name").value(brandEntity2.getName())
        );
    }

    @Test
    public void testUpdateBrand() throws Exception {
        BrandEntity testBrandEntityA = TestDataUtil.createTestBrandA();
        BrandEntity savedBrand = brandService.save(testBrandEntityA);

        BrandDto testBrandDtoA = TestDataUtil.createTestBrandDtoA();
        testBrandDtoA.setName("UpdatedName");
        String brandDtoJson = objectMapper.writeValueAsString(testBrandDtoA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/brand/" + savedBrand.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteBrandById() throws Exception {
        BrandEntity testBrandEntityA = TestDataUtil.createTestBrandA();
        BrandEntity savedBrand = brandService.save(testBrandEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/brand/" + savedBrand.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteBrandByIdWithNotFoundResponse() throws Exception {
        BrandEntity testBrandEntityA = TestDataUtil.createTestBrandA();
        BrandEntity savedBrand = brandService.save(testBrandEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/brand/" + (savedBrand.getId() + 1))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
