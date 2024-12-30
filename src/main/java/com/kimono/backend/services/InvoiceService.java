package com.kimono.backend.services;

import com.kimono.backend.domain.entities.InvoiceEntity;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {
    InvoiceEntity save(InvoiceEntity invoice);
    List<InvoiceEntity> findAll();
    Optional<InvoiceEntity> findById(Integer id);
    void deleteInvoiceById(Integer id);
    boolean doesNotExist(Integer id);
}
