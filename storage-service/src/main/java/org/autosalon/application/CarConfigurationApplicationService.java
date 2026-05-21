package org.autosalon.application;

import org.autosalon.domain.exceptions.EntityNotFoundException;
import org.autosalon.domain.model.entities.car.CarComponent;
import org.autosalon.domain.model.entities.car.CarComponentType;
import org.autosalon.domain.model.entities.car.CarConfiguration;
import org.autosalon.domain.model.entities.car.CarModel;
import org.autosalon.domain.repositories.ICarComponentRepository;
import org.autosalon.domain.repositories.ICarConfigurationRepository;
import org.autosalon.domain.repositories.ICarModelRepository;
import org.autosalon.domain.services.CarConfigurationDomainService;
import org.autosalon.presentation.dto.CarConfigurationRequestDto;
import org.autosalon.presentation.dto.CarConfigurationResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarConfigurationApplicationService {

    private final CarConfigurationDomainService carConfigurationDomainService;
    private final ICarConfigurationRepository carConfigurationRepository;
    private final ICarModelRepository carModelRepository;
    private final ICarComponentRepository componentRepository;

    public CarConfigurationApplicationService(CarConfigurationDomainService carConfigurationDomainService, ICarConfigurationRepository carConfigurationRepository, ICarModelRepository carModelRepository, ICarComponentRepository componentRepository){
        this.carConfigurationDomainService = carConfigurationDomainService;
        this.carConfigurationRepository = carConfigurationRepository;
        this.carModelRepository = carModelRepository;
        this.componentRepository = componentRepository;
    }

    public CarConfiguration createConfiguration(CarConfigurationRequestDto carConfigurationRequestDto){

        CarModel carModel = carModelRepository.findById(carConfigurationRequestDto.modelId()).orElseThrow(() -> new RuntimeException("Модель не найдена"));

        Map<CarComponentType, CarComponent> components =
                carConfigurationRequestDto.components() == null
                        ? Map.of()
                        : carConfigurationRequestDto.components().entrySet().stream()
                          .collect(Collectors.toMap(entry -> entry.getKey(), entry -> componentRepository.findById(entry.getValue()).orElseThrow(() -> new RuntimeException("Компонента не найдена"))
                          ));

        CarConfiguration configuration = carConfigurationDomainService.createConfiguration(carModel, components);

        carConfigurationRepository.save(configuration);
        return configuration;
    }

    public void updateCarConfiguration(UUID configId, CarConfigurationRequestDto carConfigurationRequestDto){

        getById(configId);

        CarModel carModel = carModelRepository.findById(carConfigurationRequestDto.modelId()).orElseThrow(() -> new RuntimeException("Модель не найдена"));

        Map<CarComponentType, CarComponent> components =
                carConfigurationRequestDto.components() == null
                        ? Map.of()
                        : carConfigurationRequestDto.components().entrySet().stream()
                          .collect(Collectors.toMap(entry -> entry.getKey(), entry -> componentRepository.findById(entry.getValue()).orElseThrow(() -> new RuntimeException("Компонента не найдена"))
                          ));

        CarConfiguration configuration = carConfigurationDomainService.createConfiguration(configId, carModel, components);

        carConfigurationRepository.save(configuration);
    }

    public List<CarConfiguration> getAllConfigurations() {
        return carConfigurationRepository.findAll();
    }


    public CarConfiguration getById(UUID id) {
        return carConfigurationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Конфигурация не найдена: " + id));
    }

    public void delete(UUID id) {
        carConfigurationRepository.delete(id);
    }
}
