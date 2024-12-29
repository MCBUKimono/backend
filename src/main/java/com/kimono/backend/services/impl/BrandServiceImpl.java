package com.kimono.backend.services.impl;

import com.kimono.backend.domain.entities.BrandEntity;
import com.kimono.backend.repositories.BrandRepository;
import com.kimono.backend.services.BrandService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public BrandEntity save(BrandEntity brandEntity) {
        return brandRepository.save(brandEntity);
    }

    @Override
    public List<BrandEntity> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Optional<BrandEntity> findById(Integer id) {
        return brandRepository.findById(id);
    }

    @Override
    public List<BrandEntity> findByName(String name) {
        return brandRepository.findByName(name);
    }

    @Override
    public void deleteBrandById(Integer id) {
        if(!brandRepository.existsById(id)){
            throw new EntityNotFoundException();
        }
        brandRepository.deleteById(id);
    }

    @Override
    public boolean doesNotExist(Integer id) {
        return !brandRepository.existsById(id);
    }

    @Override
    public BrandEntity partialUpdate(Integer id, BrandEntity brandEntity) {
        return brandRepository.findById(id).map(existingBrand ->{
            Optional.ofNullable(brandEntity.getName()).ifPresent(existingBrand::setName);
            return brandRepository.save(existingBrand);
        }).orElseThrow(() -> new RuntimeException("Brand does not exist"));
    }
}
