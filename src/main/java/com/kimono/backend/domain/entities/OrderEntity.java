package com.kimono.backend.domain.entities;

import com.kimono.backend.domain.enums.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq")
    private Integer id;

    private String shippingAddress;
    private String shippingName;
    private String trackingCode;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    private CustomerEntity customer;
}
