package com.kimono.backend.services.impl;

import com.kimono.backend.domain.entities.OrderEntity;
import com.kimono.backend.repositories.OrderRepository;
import com.kimono.backend.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderEntity save(OrderEntity order) {
        return orderRepository.save(order);
    }

    @Override
    public List<OrderEntity> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<OrderEntity> findById(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public void deleteOrderById(Integer id) {
        if(doesNotExist(id)){
            throw new EntityNotFoundException();
        }
        orderRepository.deleteById(id);
    }

    @Override
    public boolean doesNotExist(Integer id) {
        return !orderRepository.existsById(id);
    }
}
