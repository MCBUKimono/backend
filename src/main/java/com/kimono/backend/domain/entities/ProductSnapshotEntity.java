package com.kimono.backend.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_snapshots")
public class ProductSnapshotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_snapshot_id_seq")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    private ProductEntity product;

    @ManyToOne
    private InvoiceEntity invoice;

    private Integer priceCents;
    private Integer taxPer1000;
    private Integer discountPer1000;
}
