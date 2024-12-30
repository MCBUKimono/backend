package com.kimono.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kimono.backend.TestSeed.TestDataUtil;
import com.kimono.backend.domain.dto.SessionDto;
import com.kimono.backend.domain.entities.SessionEntity;
import com.kimono.backend.services.SessionService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class SessionControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    private SessionService sessionService;

    @Autowired
    public SessionControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testCreateSessionSuccessfullyReturnsHttp201Created() throws Exception {
        SessionDto testSessionDto = TestDataUtil.createTestSessionDto();

        String sessionRequestJson = objectMapper.writeValueAsString(testSessionDto);

        mockMvc.perform(post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sessionRequestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAllSessions() throws Exception {
        SessionEntity sessionEntity = TestDataUtil.createTestSessionEntity();
        sessionService.save(sessionEntity);

        mockMvc.perform(get("/session")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].token").value(sessionEntity.getToken()));
    }

    @Test
    public void testGetSessionById() throws Exception {
        SessionEntity sessionEntity = TestDataUtil.createTestSessionEntity();
        SessionEntity savedSession = sessionService.save(sessionEntity);

        mockMvc.perform(get("/session/" + savedSession.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedSession.getId()))
                .andExpect(jsonPath("$.token").value(savedSession.getToken()));
    }

    @Test
    public void testDeleteSessionById() throws Exception {
        SessionEntity sessionEntity = TestDataUtil.createTestSessionEntity();
        SessionEntity savedSession = sessionService.save(sessionEntity);

        mockMvc.perform(delete("/session/" + savedSession.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateSession() throws Exception {
        SessionEntity sessionEntity = TestDataUtil.createTestSessionEntity();
        SessionEntity savedSession = sessionService.save(sessionEntity);

        SessionDto updatedSessionDto = TestDataUtil.createTestSessionDto();
        updatedSessionDto.setToken("updated-token");
        String updatedSessionJson = objectMapper.writeValueAsString(updatedSessionDto);

        mockMvc.perform(put("/session/" + savedSession.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedSessionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("updated-token"));
    }
}
