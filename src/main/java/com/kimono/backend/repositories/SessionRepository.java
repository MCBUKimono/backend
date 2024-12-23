package com.kimono.backend.repositories;

import com.kimono.backend.domain.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Integer> {
}
