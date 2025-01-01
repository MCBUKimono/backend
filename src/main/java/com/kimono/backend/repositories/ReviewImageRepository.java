package com.kimono.backend.repositories;

import com.kimono.backend.domain.entities.ReviewImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImageEntity, Integer> {

    List<ReviewImageEntity> findByReviewId(Integer id);
}
