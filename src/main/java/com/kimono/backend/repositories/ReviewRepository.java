package com.kimono.backend.repositories;

import com.kimono.backend.domain.entities.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
    List<ReviewEntity> findByCustomerId(Integer id);

    List<ReviewEntity> findByProductId(Integer id);
}
