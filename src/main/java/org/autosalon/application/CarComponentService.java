package org.autosalon.application;

import org.autosalon.domain.exceptions.DomainValidationException;
import org.autosalon.domain.exceptions.EntityNotFoundException;
import org.autosalon.domain.model.entities.car.CarComponent;
import org.autosalon.domain.repositories.ICarComponentRepository;
import org.autosalon.domain.repositories.ICarModelRepository;
import org.autosalon.persistence.entityJpa.car.CarModelJpa;
import org.autosalon.persistence.repository.CarModelJpaRepository;
import org.autosalon.presentation.dto.CarComponentDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarComponentService {

    private final ICarComponentRepository componentRepository;
    private final ICarModelRepository modelRepository;

    public CarComponentService(ICarComponentRepository componentRepository, ICarModelRepository modelRepository) {
        this.componentRepository = componentRepository;
        this.modelRepository = modelRepository;
    }

    public CarComponent createComponent(CarComponentDto dto) {

        Set<UUID> suitableModels = dto.suitableModels() == null
                ? Set.of()
                : dto.suitableModels();

        suitableModels.forEach(id -> modelRepository.findById(id).orElseThrow(() -> new DomainValidationException("Model not found: " + id)));

        CarComponent component = new CarComponent(
                dto.type(),
                dto.name(),
                dto.price(),
                suitableModels
        );

        componentRepository.save(component);
        return component;
    }

    public CarComponent getById(UUID id) {
        return componentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Компонента не найдена: " + id));
    }

    public List<CarComponent> getAll() {
        return componentRepository.findAll();
    }

    public void delete(UUID id) {
        componentRepository.delete(id);
    }

    public void setSuitableModels(UUID componentId, Set<UUID> modelIds) {

        CarComponent component = componentRepository.findById(componentId).orElseThrow(() -> new EntityNotFoundException("Компонента не найдена: " + componentId));

        modelIds.forEach(id -> modelRepository.findById(id).orElseThrow(() -> new DomainValidationException("Модель не найдена: " + id)));

        Set<UUID> mergedModels = component.getSuitableModels() == null
                ? modelIds
                : new java.util.HashSet<>(component.getSuitableModels());

        mergedModels.addAll(modelIds);

        CarComponent updated = new CarComponent(
                component.getId(),
                component.getType(),
                component.getName(),
                component.getPrice(),
                mergedModels
        );

        componentRepository.save(updated);
    }
}
