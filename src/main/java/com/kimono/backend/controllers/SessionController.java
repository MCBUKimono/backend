package com.kimono.backend.controllers;

import com.kimono.backend.domain.dto.SessionDto;
import com.kimono.backend.domain.entities.SessionEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;
    private final Mapper<SessionEntity, SessionDto> mapper;

    @Autowired
    public SessionController(SessionService sessionService, Mapper<SessionEntity, SessionDto> mapper) {
        this.sessionService = sessionService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<SessionDto> createSession(@RequestBody SessionDto sessionDto) {
        sessionDto.setId(null);
        SessionEntity sessionEntity = mapper.mapFrom(sessionDto);
        SessionEntity savedSession = sessionService.save(sessionEntity);
        return new ResponseEntity<>(mapper.mapTo(savedSession), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SessionDto>> getAllSessions() {
        List<SessionEntity> sessions = sessionService.findAll();
        List<SessionDto> sessionDtos = sessions.stream().map(mapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(sessionDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionDto> getSessionById(@PathVariable Integer id) {
        SessionEntity session = sessionService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not found"));
        return new ResponseEntity<>(mapper.mapTo(session), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSessionById(@PathVariable Integer id) {
        try {
            sessionService.deleteSessionById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionDto> updateSession(@PathVariable Integer id, @RequestBody SessionDto sessionDto) {
        if (sessionService.doesNotExist(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not found");
        }

        sessionDto.setId(id);
        SessionEntity updatedSession = sessionService.save(mapper.mapFrom(sessionDto));
        return new ResponseEntity<>(mapper.mapTo(updatedSession), HttpStatus.OK);
    }
}
