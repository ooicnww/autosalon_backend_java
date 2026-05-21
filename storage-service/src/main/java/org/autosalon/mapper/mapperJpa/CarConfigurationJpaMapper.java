package org.autosalon.mapper.mapperJpa;

import org.autosalon.domain.model.entities.car.CarComponent;
import org.autosalon.domain.model.entities.car.CarComponentType;
import org.autosalon.domain.model.entities.car.CarConfiguration;
import org.autosalon.persistence.entityJpa.car.CarComponentJpa;
import org.autosalon.persistence.entityJpa.car.CarConfigurationJpa;
import org.autosalon.persistence.entityJpa.car.CarModelJpa;
import org.autosalon.persistence.entityJpa.car.ConfigurationComponentJpa;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CarConfigurationJpaMapper {

    private final CarComponentJpaMapper carComponentJpaMapper;
    private final CarModelJpaMapper carModelJpaMapper;


    public CarConfigurationJpaMapper(CarComponentJpaMapper carComponentJpaMapper, CarModelJpaMapper carModelJpaMapper) {
        this.carComponentJpaMapper = carComponentJpaMapper;
        this.carModelJpaMapper = carModelJpaMapper;
    }

    public CarConfiguration toDomain(CarConfigurationJpa carConfigurationJpa) {
        Map<CarComponentType, CarComponent> components = carConfigurationJpa.getComponents() == null
                ? Map.of()
                : carConfigurationJpa.getComponents().stream().collect(Collectors.toMap(configurationComponentJpa -> configurationComponentJpa.getType(), configurationComponentJpa -> carComponentJpaMapper.toDomain(configurationComponentJpa.getComponent())));

        CarConfiguration carConfiguration = new CarConfiguration(
                carConfigurationJpa.getId(),
                carModelJpaMapper.toDomain(carConfigurationJpa.getModel()),
                components
        );
        return carConfiguration;
    }

    public CarConfigurationJpa toJpa(CarConfiguration carConfiguration) {
        CarConfigurationJpa carConfigurationJpa = new CarConfigurationJpa();
        carConfigurationJpa.setId(carConfiguration.id());

        CarModelJpa model = new CarModelJpa();
        model.setId(carConfiguration.model().getId());

        carConfigurationJpa.setModel(model);


        Set<ConfigurationComponentJpa> components =
                carConfiguration.components() == null
                        ? Set.of()
                        : carConfiguration.components().entrySet().stream()
                          .map(entry -> {
                              ConfigurationComponentJpa configurationComponentJpa = new ConfigurationComponentJpa();

                              configurationComponentJpa.setId(UUID.randomUUID());
                              configurationComponentJpa.setConfiguration(carConfigurationJpa);
                              configurationComponentJpa.setType(entry.getKey());

                              CarComponentJpa componentJpa = new CarComponentJpa();
                              componentJpa.setId(entry.getValue().getId());

                              configurationComponentJpa.setComponent(componentJpa);

                              return configurationComponentJpa;
                          })
                          .collect(Collectors.toSet());

        carConfigurationJpa.setComponents(components);

        carConfigurationJpa.setTotalPrice(carConfiguration.getFullPrice());
        return carConfigurationJpa;
    }
}
