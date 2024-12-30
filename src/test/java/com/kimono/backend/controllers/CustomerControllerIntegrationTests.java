package com.kimono.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.dto.CustomerDto;
import com.kimono.backend.domain.entities.CustomerEntity;
import com.kimono.backend.services.CustomerService;
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
public class CustomerControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    public CustomerControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateCustomerSuccessfullyReturnsHttp201Created() throws Exception {
        CustomerDto testCustomerDto = TestDataUtil.createTestCustomerDto();

        String customerRequestJson = objectMapper.writeValueAsString(testCustomerDto);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerRequestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        CustomerEntity customerEntity = TestDataUtil.createTestCustomerEntity();
        customerService.save(customerEntity);

        mockMvc.perform(get("/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value(customerEntity.getName()));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        CustomerEntity customerEntity = TestDataUtil.createTestCustomerEntity();
        CustomerEntity savedCustomer = customerService.save(customerEntity);

        mockMvc.perform(get("/customers/" + savedCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedCustomer.getId()))
                .andExpect(jsonPath("$.name").value(savedCustomer.getName()));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        CustomerEntity customerEntity = TestDataUtil.createTestCustomerEntity();
        CustomerEntity savedCustomer = customerService.save(customerEntity);

        CustomerDto updatedCustomerDto = TestDataUtil.createTestCustomerDto();
        updatedCustomerDto.setName("Updated Customer");
        String updatedCustomerJson = objectMapper.writeValueAsString(updatedCustomerDto);

        mockMvc.perform(put("/customers/" + savedCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCustomerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Customer"));
    }

    @Test
    public void testPartialUpdateCustomer() throws Exception {
        CustomerEntity customerEntity = TestDataUtil.createTestCustomerEntity();
        CustomerEntity savedCustomer = customerService.save(customerEntity);

        CustomerDto updatedCustomerDto = TestDataUtil.createTestCustomerDto();
        updatedCustomerDto.setName("Updated Customer");
        String updatedCustomerJson = objectMapper.writeValueAsString(updatedCustomerDto);

        mockMvc.perform(patch("/customers/" + savedCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCustomerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Customer"));
    }

    @Test
    public void testDeleteCustomerById() throws Exception {
        CustomerEntity customerEntity = TestDataUtil.createTestCustomerEntity();
        CustomerEntity savedCustomer = customerService.save(customerEntity);

        mockMvc.perform(delete("/customers/" + savedCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
