package com.kimono.backend.domain.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "product_id_seq")
    private Integer id;

    private String name;

    @Lob
    private String description;

    @ManyToOne
    private BrandEntity brand;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
    private List<ProductImageEntity> images;

    @ManyToOne
    private CategoryEntity category;

    private Double averageScoreOutOf100;

    private Integer priceCents;
    private Integer taxPer1000;
    private Integer discountPer1000;
}
