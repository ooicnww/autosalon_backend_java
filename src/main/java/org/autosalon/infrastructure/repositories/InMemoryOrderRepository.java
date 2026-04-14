package org.autosalon.infrastructure.repositories;

import org.autosalon.domain.model.entities.car.Car;
import org.autosalon.domain.model.entities.order.Order;
import org.autosalon.domain.repositories.IOrderRepository;

import java.util.*;

public class InMemoryOrderRepository extends InMemoryRepository<Order> implements IOrderRepository {
    @Override
    public void save(Order order){
        super.save(order.getId(), order);
    }

    @Override
    public Optional<Order> findById(UUID id){
        return super.findById(id);
    }

    @Override
    public List<Order> findAll(){
        return super.findAll();
    }

    @Override
    public void delete(UUID id){
        super.delete(id);
    }
}