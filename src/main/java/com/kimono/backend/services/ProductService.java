package com.kimono.backend.services;

import com.kimono.backend.domain.entities.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductEntity save(ProductEntity productEntity);

    List<ProductEntity> findAll();

    Optional<ProductEntity> findById(Integer id);

    List<ProductEntity> findByName(String name);

    void deleteProductById(Integer id);

    boolean doesNotExist(Integer id);

    ProductEntity partialUpdate(Integer id, ProductEntity productEntity);

}
