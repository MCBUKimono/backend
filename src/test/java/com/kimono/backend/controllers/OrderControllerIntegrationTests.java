package com.kimono.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.dto.OrderDto;
import com.kimono.backend.domain.entities.OrderEntity;
import com.kimono.backend.services.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class OrderControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    public OrderControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateOrderSuccessfullyReturnsHttp201Created() throws Exception {
        OrderDto testOrderDto = TestDataUtil.createTestOrderDto();

        String orderRequestJson = objectMapper.writeValueAsString(testOrderDto);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderRequestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAllOrders() throws Exception {
        OrderEntity orderEntity = TestDataUtil.createTestOrderEntity();
        orderService.save(orderEntity);

        mockMvc.perform(get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].shippingAddress").value(orderEntity.getShippingAddress()));
    }

    @Test
    public void testGetOrderById() throws Exception {
        OrderEntity orderEntity = TestDataUtil.createTestOrderEntity();
        OrderEntity savedOrder = orderService.save(orderEntity);

        mockMvc.perform(get("/orders/" + savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedOrder.getId()))
                .andExpect(jsonPath("$.shippingAddress").value(savedOrder.getShippingAddress()));
    }

    @Test
    public void testUpdateOrder() throws Exception {
        OrderEntity orderEntity = TestDataUtil.createTestOrderEntity();
        OrderEntity savedOrder = orderService.save(orderEntity);

        OrderDto updatedOrderDto = TestDataUtil.createTestOrderDto();
        updatedOrderDto.setShippingAddress("Updated Address");
        String updatedOrderJson = objectMapper.writeValueAsString(updatedOrderDto);

        mockMvc.perform(put("/orders/" + savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedOrderJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shippingAddress").value("Updated Address"));
    }

    @Test
    public void testDeleteOrderById() throws Exception {
        OrderEntity orderEntity = TestDataUtil.createTestOrderEntity();
        OrderEntity savedOrder = orderService.save(orderEntity);

        mockMvc.perform(delete("/orders/" + savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
