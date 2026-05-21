package org.autosalon.application;
import org.autosalon.domain.exceptions.DomainValidationException;
import org.autosalon.domain.model.entities.car.CarComponent;
import org.autosalon.domain.model.entities.car.CarComponentType;
import org.autosalon.domain.model.entities.car.CarModel;
import org.autosalon.domain.repositories.ICarComponentRepository;
import org.autosalon.presentation.dto.CarModelRequestDto;
import org.springframework.stereotype.Service;


import org.autosalon.domain.repositories.ICarModelRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarModelService {
    private ICarModelRepository modelRepository;
    private ICarComponentRepository componentRepository;


    public CarModelService(ICarModelRepository modelRepository, ICarComponentRepository componentRepository) {
        this.modelRepository = modelRepository;
        this.componentRepository = componentRepository;
    }

    public CarModel createModel(CarModelRequestDto dto) {

        if (dto.defaultComponents() == null || dto.defaultComponents().isEmpty()) {
            throw new DomainValidationException("Default components are required");
        }

        Map<CarComponentType, CarComponent> defaultComponents = dto.defaultComponents()
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> componentRepository.findById(entry.getValue()).orElseThrow(() -> new DomainValidationException("Компонента не найдена: " + entry.getValue()))
                ));

        CarModel model = new CarModel(
                dto.brand(),
                dto.modelName(),
                dto.bodyType(),
                dto.fuelType(),
                dto.transmissionType(),
                dto.driveType(),
                dto.basePrice(),
                defaultComponents,
                dto.enginePower(),
                dto.engineCapacity()
        );

        modelRepository.save(model);
        return model;
    }


    public CarModel getModelById(UUID id) {
        return modelRepository.findById(id).orElseThrow(() -> new DomainValidationException("Модель не найдена"));
    }

    public List<CarModel> getAllModels() {
        return modelRepository.findAll();
    }

    public void updateModel(UUID id, CarModelRequestDto dto) {

        CarModel existing = getModelById(id);

        Map<CarComponentType, CarComponent> defaultComponents = dto.defaultComponents()
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> componentRepository.findById(entry.getValue()).orElseThrow(() -> new DomainValidationException("Компонента не найдена: " + entry.getValue()))
                ));

        CarModel updated = new CarModel(
                existing.getId(),
                dto.brand(),
                dto.modelName(),
                dto.bodyType(),
                dto.fuelType(),
                dto.transmissionType(),
                dto.driveType(),
                dto.basePrice(),
                defaultComponents,
                dto.enginePower(),
                dto.engineCapacity()
        );

        modelRepository.save(updated);
    }

    public List<CarModel> getAllModels(String brand, UUID componentId) {
        return modelRepository.findAllWithFilters(brand, componentId);
    }

    public void deleteModel(UUID id) {
        modelRepository.findById(id).orElseThrow(() -> new DomainValidationException("Модель не найдена"));
        modelRepository.delete(id);
    }
}
