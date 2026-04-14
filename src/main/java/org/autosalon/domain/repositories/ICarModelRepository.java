package org.autosalon.domain.repositories;

import org.autosalon.domain.model.entities.car.Car;
import org.autosalon.domain.model.entities.car.CarModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICarModelRepository{
    void save(CarModel model);

    Optional<CarModel> findById(UUID id);

    List<CarModel> findAll();

    public List<CarModel> findAllWithFilters(String brand, UUID componentId);

    void delete(UUID id);
}