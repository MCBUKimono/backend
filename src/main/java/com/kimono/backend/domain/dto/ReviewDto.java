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
public class ReviewDto {
    private Integer id;
    private Integer productId;
    private Integer customerId;
    private List<Integer> imageIds; // IDs of associated ReviewImageEntities
    private String text;
    private Integer scoreOutOf100;
}
