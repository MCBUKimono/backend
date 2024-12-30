package com.kimono.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Integer id;

    private String name;

    private String description;

    private Integer brandId;

    private List<Integer> imagesId;

    private Integer categoryId;

    private Double averageScoreOutOf100;

    private Integer priceCents;
    private Integer taxPer1000;
    private Integer discountPer1000;
}
