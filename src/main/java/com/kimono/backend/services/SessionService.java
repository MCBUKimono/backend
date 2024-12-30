package com.kimono.backend.services;

import com.kimono.backend.domain.entities.SessionEntity;

import java.util.List;
import java.util.Optional;

public interface SessionService {
    SessionEntity save(SessionEntity sessionEntity);

    List<SessionEntity> findAll();

    Optional<SessionEntity> findById(Integer id);

    void deleteSessionById(Integer id);

    boolean doesNotExist(Integer id);

}
