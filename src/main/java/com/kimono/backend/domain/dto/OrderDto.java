package com.kimono.backend.domain.dto;

import com.kimono.backend.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Integer id;
    private String shippingAddress;
    private String shippingName;
    private String trackingCode;
    private OrderStatus status;
    private Integer customerId;
}
