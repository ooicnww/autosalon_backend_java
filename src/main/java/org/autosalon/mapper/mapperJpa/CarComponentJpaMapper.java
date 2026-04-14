package org.autosalon.mapper.mapperJpa;

import org.autosalon.domain.model.entities.car.CarComponent;
import org.autosalon.persistence.entityJpa.car.CarComponentJpa;
import org.autosalon.persistence.entityJpa.car.CarModelJpa;
import org.autosalon.persistence.repository.CarModelJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CarComponentJpaMapper {

    public CarComponentJpa toJpa(CarComponent carComponent, Set<CarModelJpa> suitableModels){
        CarComponentJpa jpa = new CarComponentJpa();

        jpa.setId(carComponent.getId());
        jpa.setName(carComponent.getName());
        jpa.setPrice(carComponent.getPrice());
        jpa.setType(carComponent.getType());
        jpa.setSuitableModels(suitableModels);

        return jpa;

    }

    public CarComponent toDomain(CarComponentJpa carComponentJpa){
        Set<UUID> suitableModels = carComponentJpa.getSuitableModels() == null
                ? Set.of()
                : carComponentJpa.getSuitableModels().stream().map(model -> model.getId()).collect(Collectors.toSet());

        return new CarComponent(
                carComponentJpa.getId(),
                carComponentJpa.getType(),
                carComponentJpa.getName(),
                carComponentJpa.getPrice(),
                suitableModels
        );
    }
}