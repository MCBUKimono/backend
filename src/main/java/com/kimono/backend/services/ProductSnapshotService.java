package com.kimono.backend.services;

import com.kimono.backend.domain.entities.ProductSnapshotEntity;

import java.util.List;
import java.util.Optional;

public interface ProductSnapshotService {
    ProductSnapshotEntity save(ProductSnapshotEntity snapshot);
    List<ProductSnapshotEntity> findAll();
    Optional<ProductSnapshotEntity> findById(Integer id);
    void deleteSnapshotById(Integer id);
    boolean doesNotExist(Integer id);
}
