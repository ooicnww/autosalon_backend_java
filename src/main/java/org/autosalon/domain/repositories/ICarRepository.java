package org.autosalon.domain.repositories;

import org.autosalon.domain.model.entities.car.Car;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICarRepository{
    void save(Car car);

    Optional<Car> findById(UUID id);

    List<Car> findAll();

    void delete(UUID id);
}