package org.autosalon.infrastructure.repositories;

import org.autosalon.domain.model.entities.car.Car;
import org.autosalon.domain.model.entities.car.Part;
import org.autosalon.domain.model.entities.order.Order;
import org.autosalon.domain.repositories.IOrderRepository;
import org.autosalon.domain.repositories.IPartRepository;

import java.util.*;

public class InMemoryPartRepository extends InMemoryRepository<Part> implements IPartRepository {
    @Override
    public void save(Part part){
        super.save(part.getId(), part);
    }

    @Override
    public Optional<Part> findById(UUID id){
        return super.findById(id);
    }

    @Override
    public List<Part> findAll(){
        return super.findAll();
    }

    @Override
    public void delete(UUID id){
        super.delete(id);
    }
}