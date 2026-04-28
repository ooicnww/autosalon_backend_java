package org.autosalon.mapper.mapperDto;

import java.util.Map;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.autosalon.domain.model.entities.car.CarComponentType;
import org.autosalon.domain.model.entities.car.CarConfiguration;
import org.autosalon.domain.model.entities.car.CarModel;
import org.autosalon.presentation.dto.CarComponentDto;
import org.autosalon.presentation.dto.CarConfigurationResponseDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-14T15:43:24+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.jar, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class CarConfigurationResponseDtoMapperImpl implements CarConfigurationResponseDtoMapper {

    @Override
    public CarConfigurationResponseDto toDto(CarConfiguration carConfiguration) {
        if ( carConfiguration == null ) {
            return null;
        }

        UUID modelId = null;
        UUID id = null;

        modelId = carConfigurationModelId( carConfiguration );
        id = carConfiguration.id();

        int totalPrice = carConfiguration.getFullPrice();
        Map<CarComponentType, CarComponentDto> components = mapComponents(carConfiguration);

        CarConfigurationResponseDto carConfigurationResponseDto = new CarConfigurationResponseDto( id, modelId, components, totalPrice );

        return carConfigurationResponseDto;
    }

    private UUID carConfigurationModelId(CarConfiguration carConfiguration) {
        if ( carConfiguration == null ) {
            return null;
        }
        CarModel model = carConfiguration.model();
        if ( model == null ) {
            return null;
        }
        UUID id = model.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
