package org.autosalon.domain.repositories;

import org.autosalon.domain.model.entities.car.CarComponent;
import org.autosalon.domain.model.entities.car.CarModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICarComponentRepository {
    void save(CarComponent carComponent);

    Optional<CarComponent> findById(UUID id);

    List<CarComponent> findAll();

    void delete(UUID id);
}
