package com.kimono.backend.services.impl;

import com.kimono.backend.domain.entities.ProductSnapshotEntity;
import com.kimono.backend.repositories.ProductSnapshotRepository;
import com.kimono.backend.services.ProductSnapshotService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSnapshotServiceImpl implements ProductSnapshotService {

    private final ProductSnapshotRepository snapshotRepository;

    public ProductSnapshotServiceImpl(ProductSnapshotRepository snapshotRepository) {
        this.snapshotRepository = snapshotRepository;
    }

    @Override
    public ProductSnapshotEntity save(ProductSnapshotEntity snapshot) {
        return snapshotRepository.save(snapshot);
    }

    @Override
    public List<ProductSnapshotEntity> findAll() {
        return snapshotRepository.findAll();
    }

    @Override
    public Optional<ProductSnapshotEntity> findById(Integer id) {
        return snapshotRepository.findById(id);
    }

    @Override
    public void deleteSnapshotById(Integer id) {
        if (doesNotExist(id)) {
            throw new EntityNotFoundException("Product Snapshot not found");
        }
        snapshotRepository.deleteById(id);
    }

    @Override
    public boolean doesNotExist(Integer id) {
        return !snapshotRepository.existsById(id);
    }
}
