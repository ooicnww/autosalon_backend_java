package org.autosalon.mapper.mapperDto;

import java.util.UUID;
import javax.annotation.processing.Generated;
import org.autosalon.domain.model.entities.order.Order;
import org.autosalon.domain.model.entities.order.OrderStatus;
import org.autosalon.domain.model.users.Client;
import org.autosalon.domain.model.users.Manager;
import org.autosalon.presentation.dto.OrderResponseDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-14T15:43:24+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.jar, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class OrderResponseMapperImpl implements OrderResponseMapper {

    @Override
    public OrderResponseDto toDto(Order order) {
        if ( order == null ) {
            return null;
        }

        UUID clientId = null;
        UUID managerId = null;
        UUID id = null;
        OrderStatus status = null;

        clientId = orderClientId( order );
        managerId = orderManagerId( order );
        id = order.getId();
        status = order.getStatus();

        UUID carId = extractCarId(order);
        UUID configurationId = extractConfigurationId(order);

        OrderResponseDto orderResponseDto = new OrderResponseDto( id, clientId, managerId, carId, configurationId, status );

        return orderResponseDto;
    }

    private UUID orderClientId(Order order) {
        if ( order == null ) {
            return null;
        }
        Client client = order.getClient();
        if ( client == null ) {
            return null;
        }
        UUID id = client.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private UUID orderManagerId(Order order) {
        if ( order == null ) {
            return null;
        }
        Manager manager = order.getManager();
        if ( manager == null ) {
            return null;
        }
        UUID id = manager.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
