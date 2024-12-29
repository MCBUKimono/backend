package com.kimono.backend.repositories;

import com.kimono.backend.domain.entities.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Integer> {

    List<BrandEntity> findByName(String name);
}
