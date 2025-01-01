package com.kimono.backend.repositories;

import com.kimono.backend.domain.entities.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Integer> {
    List<InvoiceEntity> findByCustomerId(Integer id);
}
