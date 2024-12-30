package com.kimono.backend.services.impl;

import com.kimono.backend.domain.entities.SessionEntity;
import com.kimono.backend.repositories.SessionRepository;
import com.kimono.backend.services.SessionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public SessionEntity save(SessionEntity sessionEntity) {
        return sessionRepository.save(sessionEntity);
    }

    @Override
    public List<SessionEntity> findAll() {
        return sessionRepository.findAll();
    }

    @Override
    public Optional<SessionEntity> findById(Integer id) {
        return sessionRepository.findById(id);
    }

    @Override
    public void deleteSessionById(Integer id) {
        if(doesNotExist(id)){
            throw new EntityNotFoundException();
        }
        sessionRepository.deleteById(id);
    }

    @Override
    public boolean doesNotExist(Integer id) {
        return !sessionRepository.existsById(id);
    }

}
