package org.autosalon.mapper.mapperDto;

import org.autosalon.domain.model.entities.order.CustomOrder;
import org.autosalon.domain.model.entities.order.ExistedCarOrder;
import org.autosalon.domain.model.entities.order.Order;
import org.autosalon.presentation.dto.OrderResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OrderResponseMapper {

    @Mapping(target = "carId", expression = "java(extractCarId(order))")
    @Mapping(target = "configurationId", expression = "java(extractConfigurationId(order))")
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "managerId", source = "manager.id")
    OrderResponseDto toDto(Order order);

    default UUID extractCarId(Order order) {
        if (order instanceof ExistedCarOrder existed) {
            return existed.getCar().getId();
        }
        return null;
    }

    default UUID extractConfigurationId(Order order) {
        if (order instanceof CustomOrder custom) {
            return custom.getConfiguration().id();
        }
        return null;
    }
}