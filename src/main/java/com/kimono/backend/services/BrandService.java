package com.kimono.backend.services;

import com.kimono.backend.domain.entities.BrandEntity;

import java.util.List;
import java.util.Optional;

public interface BrandService {

    BrandEntity save(BrandEntity brandEntity);

    List<BrandEntity> findAll();

    Optional<BrandEntity> findById(Integer id);

    List<BrandEntity> findByName(String name);

    void deleteBrandById(Integer id);

    boolean doesNotExist(Integer id);

    BrandEntity partialUpdate(Integer id, BrandEntity brandEntity);
}
