package com.kimono.backend.controllers;

import com.kimono.backend.domain.dto.OrderDto;
import com.kimono.backend.domain.entities.OrderEntity;
import com.kimono.backend.mappers.Mapper;
import com.kimono.backend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final Mapper<OrderEntity, OrderDto> mapper;

    @Autowired
    public OrderController(OrderService orderService, Mapper<OrderEntity, OrderDto> mapper) {
        this.orderService = orderService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        OrderEntity orderEntity = mapper.mapFrom(orderDto);
        OrderEntity savedOrder = orderService.save(orderEntity);
        return new ResponseEntity<>(mapper.mapTo(savedOrder), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderEntity> orders = orderService.findAll();
        List<OrderDto> orderDtos = orders.stream().map(mapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Integer id) {
        OrderEntity order = orderService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        return new ResponseEntity<>(mapper.mapTo(order), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Integer id, @RequestBody OrderDto orderDto) {
        if (orderService.doesNotExist(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }

        orderDto.setId(id);
        OrderEntity updatedOrder = orderService.save(mapper.mapFrom(orderDto));
        return new ResponseEntity<>(mapper.mapTo(updatedOrder), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable Integer id) {
        try {
            orderService.deleteOrderById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
