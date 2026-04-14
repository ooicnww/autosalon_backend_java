package org.autosalon.mapper.mapperDto;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.autosalon.domain.model.entities.car.CarComponent;
import org.autosalon.domain.model.entities.car.CarComponentType;
import org.autosalon.presentation.dto.CarComponentDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-03T00:42:28+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.jar, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class CarComponentDtoMapperImpl implements CarComponentDtoMapper {

    @Override
    public CarComponentDto toDto(CarComponent component) {
        if ( component == null ) {
            return null;
        }

        UUID id = null;
        CarComponentType type = null;
        String name = null;
        int price = 0;
        Set<UUID> suitableModels = null;

        id = component.getId();
        type = component.getType();
        name = component.getName();
        price = component.getPrice();
        Set<UUID> set = component.getSuitableModels();
        if ( set != null ) {
            suitableModels = new LinkedHashSet<UUID>( set );
        }

        CarComponentDto carComponentDto = new CarComponentDto( id, type, name, price, suitableModels );

        return carComponentDto;
    }
}
