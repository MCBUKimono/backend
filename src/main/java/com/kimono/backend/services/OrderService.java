package com.kimono.backend.services;

import com.kimono.backend.domain.entities.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    OrderEntity save(OrderEntity order);

    List<OrderEntity> findAll();

    Optional<OrderEntity> findById(Integer id);

    void deleteOrderById(Integer id);

    boolean doesNotExist(Integer id);
}
