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
public class CategoryDto {

    private Integer id;
    private String name;
    private Integer parentCategoryId; // Üst kategori ID'si
    private List<Integer> subCategoryIds; // Alt kategori ID'leri
    private List<Integer> productIds;


}
