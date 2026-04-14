package org.autosalon.mapper.mapperDto;

import org.autosalon.domain.model.entities.testDrive.TestRequest;
import org.autosalon.presentation.dto.TestRequestResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestRequestDtoMapper {

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "carId", source = "car.id")
    TestRequestResponseDto toDto(TestRequest domain);
}