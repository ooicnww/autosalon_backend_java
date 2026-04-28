package org.autosalon.presentation.controller;

import org.autosalon.application.OrderService;
import org.autosalon.domain.model.entities.order.Order;
import org.autosalon.mapper.mapperDto.OrderResponseMapper;
import org.autosalon.presentation.dto.OrderRequestDto;
import org.autosalon.presentation.dto.OrderResponseDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderResponseMapper mapper;

    public OrderController(OrderService orderService, OrderResponseMapper mapper) {
        this.orderService = orderService;
        this.mapper = mapper;
    }

    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @GetMapping
    public List<OrderResponseDto> getAll() {
        return orderService.getAllOrders().stream().map(mapper::toDto).toList();
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/existed")
    public void createExisted(@RequestBody OrderRequestDto dto) {
        orderService.createExistedCarOrder(dto.carId());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/custom")
    public void createCustom(@RequestBody OrderRequestDto dto) {
        orderService.createCustomOrder(dto.configurationId());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}/approve")
    public void approve(@PathVariable UUID id) {
        orderService.approveOrder(id);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/pay")
    public void pay(@PathVariable UUID id) {
        orderService.payOrder(id);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/complete")
    public void complete(@PathVariable UUID id) {
        orderService.completeOrder(id);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        orderService.deleteOrder(id);
    }
}