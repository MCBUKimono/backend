package com.kimono.backend.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
@Table(name = "rewiews")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_id_seq")
    private Integer id;

    @ManyToOne
    private ProductEntity product;

    @ManyToOne
    private CustomerEntity customer;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewImageEntity> images;

    @Lob
    private String text;

    private Integer scoreOutOf100;
}
