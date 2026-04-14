package org.autosalon.infrastructure.repositories;

import org.autosalon.domain.model.entities.car.Car;
import org.autosalon.domain.repositories.ICarRepository;

import java.util.*;

public class InMemoryCarRepository extends InMemoryRepository<Car> implements ICarRepository {
    @Override
    public void save(Car car){
        super.save(car.getId(), car);
    }

    @Override
    public Optional<Car> findById(UUID id){
        return super.findById(id);
    }

    @Override
    public List<Car> findAll(){
        return super.findAll();
    }

    @Override
    public void delete(UUID id){
        super.delete(id);
    }
}