package com.kimono.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSnapshotDto {
    private Integer id;
    private Integer productId;
    private Integer invoiceId;
    private Integer priceCents;
    private Integer taxPer1000;
    private Integer discountPer1000;
}
