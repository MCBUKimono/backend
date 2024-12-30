package com.kimono.backend.controllers;

import com.kimono.backend.domain.dto.InvoiceDto;
import com.kimono.backend.domain.entities.InvoiceEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final Mapper<InvoiceEntity, InvoiceDto> mapper;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, Mapper<InvoiceEntity, InvoiceDto> mapper) {
        this.invoiceService = invoiceService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<InvoiceDto> createInvoice(@RequestBody InvoiceDto invoiceDto) {
        InvoiceEntity invoiceEntity = mapper.mapFrom(invoiceDto);
        InvoiceEntity savedInvoice = invoiceService.save(invoiceEntity);
        return new ResponseEntity<>(mapper.mapTo(savedInvoice), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDto>> getAllInvoices() {
        List<InvoiceEntity> invoices = invoiceService.findAll();
        List<InvoiceDto> invoiceDtos = invoices.stream().map(mapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(invoiceDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable Integer id) {
        InvoiceEntity invoice = invoiceService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found"));
        return new ResponseEntity<>(mapper.mapTo(invoice), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDto> updateInvoice(@PathVariable Integer id, @RequestBody InvoiceDto invoiceDto) {
        if (invoiceService.doesNotExist(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found");
        }

        invoiceDto.setId(id);
        InvoiceEntity updatedInvoice = invoiceService.save(mapper.mapFrom(invoiceDto));
        return new ResponseEntity<>(mapper.mapTo(updatedInvoice), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvoiceById(@PathVariable Integer id) {
        try {
            invoiceService.deleteInvoiceById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
