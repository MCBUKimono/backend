package com.kimono.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.dto.ProductDto;
import com.kimono.backend.domain.entities.ProductEntity;
import com.kimono.backend.services.ProductService;
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
public class ProductControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    @Autowired
    private ProductService productService;

    @Autowired
    public ProductControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateProductSuccessfullyReturnsHttp201Created() throws Exception {
        ProductDto testProductDtoA = TestDataUtil.createTestProductDtoA();

        String productRequestJson = objectMapper.writeValueAsString(testProductDtoA);

        mockMvc.perform(MockMvcRequestBuilders.post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetAllProducts() throws Exception {
        ProductEntity productEntity = TestDataUtil.createTestProductA();
        productService.save(productEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/product")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(productEntity.getName())
        );
    }

    @Test
    public void testGetProductById() throws Exception {
        ProductEntity productEntity = TestDataUtil.createTestProductA();
        ProductEntity savedProductEntity = productService.save(productEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/product/" + savedProductEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(productEntity.getName())
        );
    }

    @Test
    public void testGetProductsByName() throws Exception {
        ProductEntity productEntity1 = TestDataUtil.createTestProductA();
        ProductEntity productEntity2 = TestDataUtil.createTestProductA();
        productService.save(productEntity1);
        productService.save(productEntity2);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/product/name/" +productEntity1.getName())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(productEntity1.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].name").value(productEntity2.getName())
        );
    }

    @Test
    public void testUpdateProduct() throws Exception {
        ProductEntity testProductEntityA = TestDataUtil.createTestProductA();
        ProductEntity savedProduct = productService.save(testProductEntityA);

        ProductDto testProductDtoA = TestDataUtil.createTestProductDtoA();
        testProductDtoA.setName("Updateddddd");
        String productDtoJson = objectMapper.writeValueAsString(testProductDtoA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/product/" + savedProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testPartialUpdateProduct() throws Exception {
        ProductEntity testProductEntityA = TestDataUtil.createTestProductA();
        ProductEntity savedProduct = productService.save(testProductEntityA);


        ProductDto testProductDtoA = TestDataUtil.createTestProductDtoA();
        testProductDtoA.setName("Updateddddd");
        String productDtoJson = objectMapper.writeValueAsString(testProductDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/product/" + savedProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteProductById() throws Exception {
        ProductEntity testProductEntityA = TestDataUtil.createTestProductA();
        ProductEntity savedProduct = productService.save(testProductEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/product/" + savedProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testDeleteProductByIdWithNotFoundResponse() throws Exception {
        ProductEntity testProductEntityA = TestDataUtil.createTestProductA();
        ProductEntity savedProduct = productService.save(testProductEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/product/" + (savedProduct.getId()+1))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());

    }


}
