package org.autosalon.mapper.mapperDto;

import org.autosalon.domain.model.entities.car.CarComponent;
import org.autosalon.presentation.dto.CarComponentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarComponentDtoMapper {
    CarComponentDto toDto(CarComponent component);
}
