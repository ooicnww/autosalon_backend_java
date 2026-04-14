package org.autosalon.mapper.mapperDto;

import org.autosalon.domain.model.entities.car.CarComponent;
import org.autosalon.domain.model.entities.car.CarComponentType;
import org.autosalon.domain.model.entities.car.CarModel;
import org.autosalon.presentation.dto.CarComponentDto;
import org.autosalon.presentation.dto.CarModelResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CarModelResponseDtoMapper {

    @Mapping(target = "defaultComponents", expression = "java(mapComponents(model))")
    CarModelResponseDto toDto(CarModel model);

    default Map<CarComponentType, CarComponentDto> mapComponents(CarModel model) {
        return model.getDefaultComponents() == null
                ? Map.of()
                : model.getDefaultComponents().entrySet().stream()
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