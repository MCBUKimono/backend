package com.kimono.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.dto.ProductSnapshotDto;
import com.kimono.backend.domain.entities.ProductSnapshotEntity;
import com.kimono.backend.services.ProductSnapshotService;
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
public class ProductSnapshotControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    private ProductSnapshotService snapshotService;

    @Autowired
    public ProductSnapshotControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateSnapshotSuccessfully() throws Exception {
        ProductSnapshotDto snapshotDto = TestDataUtil.createTestProductSnapshotDto();

        String snapshotRequestJson = objectMapper.writeValueAsString(snapshotDto);

        mockMvc.perform(post("/product-snapshots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(snapshotRequestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAllSnapshots() throws Exception {
        ProductSnapshotEntity snapshotEntity = TestDataUtil.createTestProductSnapshotEntity();
        snapshotService.save(snapshotEntity);

        mockMvc.perform(get("/product-snapshots")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber());
    }

    @Test
    public void testGetSnapshotsById() throws Exception {
        ProductSnapshotEntity snapshotEntity = TestDataUtil.createTestProductSnapshotEntity();
        snapshotService.save(snapshotEntity);

        mockMvc.perform(get("/product-snapshots/"+snapshotEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(snapshotEntity.getId()));
    }

    @Test
    public void testUpdateSnapshot() throws Exception {
        ProductSnapshotEntity snapshotEntity = TestDataUtil.createTestProductSnapshotEntity();
        ProductSnapshotEntity savedSnapshot = snapshotService.save(snapshotEntity);

        ProductSnapshotDto updatedSnapshotDto = TestDataUtil.createTestProductSnapshotDto();
        updatedSnapshotDto.setPriceCents(20000);

        String updatedSnapshotJson = objectMapper.writeValueAsString(updatedSnapshotDto);

        mockMvc.perform(put("/product-snapshots/" + savedSnapshot.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedSnapshotJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceCents").value(20000));
    }

    @Test
    public void testDeleteSnapshotById() throws Exception {
        ProductSnapshotEntity snapshotEntity = TestDataUtil.createTestProductSnapshotEntity();
        ProductSnapshotEntity savedSnapshot = snapshotService.save(snapshotEntity);

        mockMvc.perform(delete("/product-snapshots/" + savedSnapshot.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
