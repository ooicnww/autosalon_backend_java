package org.autosalon.mapper.mapperDto;

import org.autosalon.domain.model.entities.car.Car;
import org.autosalon.presentation.dto.CarDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarDtoMapper {
    @Mapping(target = "modelId", source = "model.id")
    CarDto toDto(Car car);
}