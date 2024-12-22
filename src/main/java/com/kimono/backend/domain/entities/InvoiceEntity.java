package com.kimono.backend.domain.entities;

import com.kimono.backend.domain.enums.PaymentMethod;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "invoices")
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_id_seq")
    private Integer id;

    private Integer invoiceNumber;

    @ManyToOne
    private CustomerEntity customer;

    private String invoiceAddress;
    private String invoiceName;

    @OneToMany(mappedBy = "invoice")
    private List<ProductSnapshotEntity> productSnapshotEntities;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
}
