package com.kimono.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewImageDto {

    private Integer id;

    private Integer displayOrder;

    private Integer reviewId;

    private String url;
}
