package com.kimono.backend.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "review_images")
public class ReviewImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_image_id_seq")
    private Integer id;

    @ManyToOne
    private ReviewEntity review;

    private Integer displayOrder;

    private String url;

}
