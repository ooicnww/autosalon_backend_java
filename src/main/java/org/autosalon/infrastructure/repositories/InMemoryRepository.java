package org.autosalon.infrastructure.repositories;

import org.autosalon.domain.model.entities.car.Car;
import org.autosalon.domain.repositories.ICarRepository;

import java.util.*;

public abstract class InMemoryRepository<T>{
    private Map<UUID, T> storage = new HashMap<>();

    public void save(UUID id, T entity){
        storage.put(id, entity);
    }

    public Optional<T> findById(UUID id){
        return Optional.ofNullable(storage.get(id));
    }

    public List<T> findAll(){
        return new ArrayList<>((storage.values()));
    }

    public void delete(UUID id){
        storage.remove(id);
    }
}