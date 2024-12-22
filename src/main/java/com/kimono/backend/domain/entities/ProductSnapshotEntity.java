package com.kimono.backend.domain.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ProductSnapshotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_snapshot_id_seq")
    private Integer id;

    @ManyToOne
    private ProductEntity product;

    @ManyToOne
    private InvoiceEntity invoice;

    private Integer priceCents;
    private Integer taxPer1000;
    private Integer discountPer1000;
}
