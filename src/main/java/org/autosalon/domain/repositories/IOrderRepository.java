package org.autosalon.domain.repositories;

import org.autosalon.domain.model.entities.order.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IOrderRepository{
    void save(Order order);

    Optional<Order> findById(UUID id);

    List<Order> findAll();

    void delete(UUID id);
}