package org.autosalon.mapper.mapperJpa;

import org.autosalon.domain.model.entities.car.Car;
import org.autosalon.persistence.entityJpa.car.CarJpa;
import org.autosalon.persistence.entityJpa.car.CarModelJpa;
import org.springframework.stereotype.Component;

@Component
public class CarJpaMapper {
    private final CarModelJpaMapper modelMapper;

    public CarJpaMapper(CarModelJpaMapper modelMapper) {

        this.modelMapper = modelMapper;
    }

    public CarJpa toJpa(Car car) {
        CarJpa jpa = new CarJpa();

        jpa.setId(car.getId());
        jpa.setColor(car.getColor());
        jpa.setAvailable(car.isAvailable());
        jpa.setPrice(car.getPrice());

        CarModelJpa model = new CarModelJpa();
        model.setId(car.getModel().getId());

        jpa.setModel(model);
        return jpa;
    }

    public Car toDomain(CarJpa jpa) {
        return new Car(
                jpa.getId(),
                modelMapper.toDomain(jpa.getModel()),
                jpa.getColor(),
                jpa.isAvailable(),
                jpa.getPrice()
        );
    }

}
