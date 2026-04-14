package org.autosalon.mapper.mapperDto;


import org.autosalon.domain.model.entities.car.CarComponent;
import org.autosalon.domain.model.entities.car.CarComponentType;
import org.autosalon.domain.model.entities.car.CarConfiguration;
import org.autosalon.domain.model.entities.car.CarModel;
import org.autosalon.presentation.dto.CarComponentDto;
import org.autosalon.presentation.dto.CarConfigurationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")

public interface CarConfigurationResponseDtoMapper {

    @Mapping(target = "modelId", source = "model.id")
    @Mapping(target = "totalPrice", expression = "java(carConfiguration.getFullPrice())")
    @Mapping(target = "components", expression = "java(mapComponents(carConfiguration))")
    CarConfigurationResponseDto toDto(CarConfiguration carConfiguration);

    default Map<CarComponentType, CarComponentDto> mapComponents(CarConfiguration carConfiguration) {
        return carConfiguration.components() == null
                ? Map.of()
                : carConfiguration.components().entrySet().stream()
                  .collect(Collectors.toMap(
                          Map.Entry::getKey,
                          entry -> toComponentDto(entry.getValue())
                  ));
    }

    default CarComponentDto toComponentDto(CarComponent component) {
        return new CarComponentDto(
                component.getId(),
                component.getType(),
                component.getName(),
                component.getPrice(),
                component.getSuitableModels() == null
                        ? Set.of()
                        : component.getSuitableModels()
        );
    }
}
