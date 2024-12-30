package com.kimono.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.dto.InvoiceDto;
import com.kimono.backend.domain.entities.InvoiceEntity;
import com.kimono.backend.services.InvoiceService;
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
public class InvoiceControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    public InvoiceControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    // Create Test
    @Test
    public void testCreateInvoiceSuccessfully() throws Exception {
        InvoiceDto testInvoiceDto = TestDataUtil.createTestInvoiceDto();

        String invoiceRequestJson = objectMapper.writeValueAsString(testInvoiceDto);

        mockMvc.perform(post("/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invoiceRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.invoiceNumber").value(testInvoiceDto.getInvoiceNumber()));
    }

    // Get All Test
    @Test
    public void testGetAllInvoices() throws Exception {
        InvoiceEntity invoiceEntity = TestDataUtil.createTestInvoiceEntity();
        invoiceService.save(invoiceEntity);

        mockMvc.perform(get("/invoices")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].invoiceNumber").value(invoiceEntity.getInvoiceNumber()));
    }

    // Get by ID Test
    @Test
    public void testGetInvoiceByIdSuccessfully() throws Exception {
        InvoiceEntity invoiceEntity = TestDataUtil.createTestInvoiceEntity();
        InvoiceEntity savedInvoice = invoiceService.save(invoiceEntity);

        mockMvc.perform(get("/invoices/" + savedInvoice.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedInvoice.getId()))
                .andExpect(jsonPath("$.invoiceNumber").value(savedInvoice.getInvoiceNumber()));
    }

    @Test
    public void testGetInvoiceByIdNotFound() throws Exception {
        mockMvc.perform(get("/invoices/99999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // Update Test
    @Test
    public void testUpdateInvoiceSuccessfully() throws Exception {
        InvoiceEntity invoiceEntity = TestDataUtil.createTestInvoiceEntity();
        InvoiceEntity savedInvoice = invoiceService.save(invoiceEntity);

        InvoiceDto updatedInvoiceDto = TestDataUtil.createTestInvoiceDto();
        updatedInvoiceDto.setInvoiceAddress("Updated Address");

        String updatedInvoiceJson = objectMapper.writeValueAsString(updatedInvoiceDto);

        mockMvc.perform(put("/invoices/" + savedInvoice.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedInvoiceJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.invoiceAddress").value("Updated Address"));
    }

    @Test
    public void testUpdateInvoiceNotFound() throws Exception {
        InvoiceDto updatedInvoiceDto = TestDataUtil.createTestInvoiceDto();
        updatedInvoiceDto.setInvoiceAddress("Updated Address");

        String updatedInvoiceJson = objectMapper.writeValueAsString(updatedInvoiceDto);

        mockMvc.perform(put("/invoices/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedInvoiceJson))
                .andExpect(status().isNotFound());
    }

    // Delete Test
    @Test
    public void testDeleteInvoiceByIdSuccessfully() throws Exception {
        InvoiceEntity invoiceEntity = TestDataUtil.createTestInvoiceEntity();
        InvoiceEntity savedInvoice = invoiceService.save(invoiceEntity);

        mockMvc.perform(delete("/invoices/" + savedInvoice.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteInvoiceByIdNotFound() throws Exception {
        mockMvc.perform(delete("/invoices/99999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
