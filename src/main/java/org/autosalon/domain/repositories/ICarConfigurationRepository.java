package org.autosalon.domain.repositories;

import org.autosalon.domain.model.entities.car.CarConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ICarConfigurationRepository {
    void save(CarConfiguration carConfiguration);

    Optional<CarConfiguration> findById(UUID id);

    List<CarConfiguration> findAll();

    void delete(UUID id);
}
