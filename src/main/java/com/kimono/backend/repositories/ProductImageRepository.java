package com.kimono.backend.repositories;

import com.kimono.backend.domain.entities.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Integer> {
    List<ProductImageEntity> findByProductId(Integer id);
}
