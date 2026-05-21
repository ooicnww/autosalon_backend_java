package org.autosalon.domain.services;

import org.autosalon.domain.exceptions.DomainValidationException;
import org.autosalon.domain.exceptions.NotSuitableComponentException;
import org.autosalon.domain.model.entities.car.CarComponent;
import org.autosalon.domain.model.entities.car.CarComponentType;
import org.autosalon.domain.model.entities.car.CarConfiguration;
import org.autosalon.domain.model.entities.car.CarModel;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class CarConfigurationDomainService {

    public CarConfiguration createConfiguration(CarModel model, Map<CarComponentType, CarComponent> components){
        validateConfiguration(model, components);
        return new CarConfiguration(UUID.randomUUID(), model, components);
    }

    public CarConfiguration createConfiguration(UUID id, CarModel model, Map<CarComponentType, CarComponent> components){
        validateConfiguration(model, components);
        return new CarConfiguration(id, model, components);
    }

    public void validateConfiguration(CarModel model, Map<CarComponentType, CarComponent> components){
        for (CarComponentType type: CarComponentType.values()){
            if (!components.containsKey(type)){
                throw new DomainValidationException("Отсутствует обязательный узел " + type);
            }
        }
        for (CarComponent component: components.values()){
            if (!component.getSuitableModels().contains(model.getId())){
                throw new NotSuitableComponentException("Выбранный компонент" + component + "недоступен для модели" + model);
            }

        }
    }
}