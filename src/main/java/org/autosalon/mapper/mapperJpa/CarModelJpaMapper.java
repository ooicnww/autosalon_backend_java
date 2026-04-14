package org.autosalon.mapper.mapperJpa;

import org.autosalon.domain.model.entities.car.CarComponent;
import org.autosalon.domain.model.entities.car.CarComponentType;
import org.autosalon.domain.model.entities.car.CarModel;
import org.autosalon.persistence.entityJpa.car.CarComponentJpa;
import org.autosalon.persistence.entityJpa.car.CarModelDefaultComponentsJpa;
import org.autosalon.persistence.entityJpa.car.CarModelJpa;
import org.autosalon.persistence.repository.CarComponentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class CarModelJpaMapper {

    private final CarComponentJpaMapper carComponentMapper;

    public CarModelJpaMapper(CarComponentJpaMapper carComponentMapper) {
        this.carComponentMapper = carComponentMapper;
    }

    public CarModelJpa toJpa(CarModel model, Map<CarComponentType, CarComponentJpa> defaultComponentsMap) {
        CarModelJpa jpa = new CarModelJpa();

        Set<CarModelDefaultComponentsJpa> defaultComponents = defaultComponentsMap.entrySet().stream().map(entry -> {
            CarModelDefaultComponentsJpa components = new CarModelDefaultComponentsJpa();
            components.setId(UUID.randomUUID());
            components.setModel(jpa);
            components.setType(entry.getKey());
            components.setComponent(entry.getValue());

            return components;
        }).collect(Collectors.toSet());

        jpa.setId(model.getId());
        jpa.setBrand(model.getBrand());
        jpa.setModelName(model.getModelName());
        jpa.setBodyType(model.getBodyType());
        jpa.setFuelType(model.getFuelType());
        jpa.setTransmissionType(model.getTransmissionType());
        jpa.setDriveType(model.getDriveType());
        jpa.setBasePrice(model.getBasePrice());
        jpa.setEnginePower(model.getEnginePower());
        jpa.setEngineCapacity(model.getEngineCapacity());
        jpa.setDefaultComponents(defaultComponents);

        return jpa;
    }

    public CarModel toDomain(CarModelJpa jpa) {
        Map<CarComponentType, CarComponent> defaultComponents = jpa.getDefaultComponents() == null
                ? new HashMap<>()
                : jpa.getDefaultComponents().stream().collect(Collectors.toMap(defaultComponentsJpa -> defaultComponentsJpa.getType(), defaultComponentsJpa -> carComponentMapper.toDomain(defaultComponentsJpa.getComponent())));

        return new CarModel(
                jpa.getId(),
                jpa.getBrand(),
                jpa.getModelName(),
                jpa.getBodyType(),
                jpa.getFuelType(),
                jpa.getTransmissionType(),
                jpa.getDriveType(),
                jpa.getBasePrice(),
                defaultComponents,
                jpa.getEnginePower(),
                jpa.getEngineCapacity()
        );
    }
}