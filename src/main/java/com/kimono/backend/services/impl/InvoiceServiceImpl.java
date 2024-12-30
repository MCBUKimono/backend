package com.kimono.backend.services.impl;

import com.kimono.backend.domain.entities.InvoiceEntity;
import com.kimono.backend.repositories.InvoiceRepository;
import com.kimono.backend.services.InvoiceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public InvoiceEntity save(InvoiceEntity invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public List<InvoiceEntity> findAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Optional<InvoiceEntity> findById(Integer id) {
        return invoiceRepository.findById(id);
    }

    @Override
    public void deleteInvoiceById(Integer id) {
        if (doesNotExist(id)) {
            throw new EntityNotFoundException("Invoice not found");
        }
        invoiceRepository.deleteById(id);
    }

    @Override
    public boolean doesNotExist(Integer id) {
        return !invoiceRepository.existsById(id);
    }
}
