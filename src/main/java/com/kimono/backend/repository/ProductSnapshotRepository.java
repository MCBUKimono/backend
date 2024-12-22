package com.kimono.backend.repository;

import com.kimono.backend.domain.entities.ProductSnapshotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSnapshotRepository extends JpaRepository<ProductSnapshotEntity, Integer> {
}
