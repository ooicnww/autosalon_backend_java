package org.autosalon.mapper.mapperDto;

import java.util.UUID;
import javax.annotation.processing.Generated;
import org.autosalon.domain.model.entities.car.Car;
import org.autosalon.domain.model.entities.car.CarModel;
import org.autosalon.presentation.dto.CarDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-03T00:42:28+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.jar, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class CarDtoMapperImpl implements CarDtoMapper {

    @Override
    public CarDto toDto(Car car) {
        if ( car == null ) {
            return null;
        }

        UUID modelId = null;
        UUID id = null;
        String color = null;
        boolean available = false;
        int price = 0;

        modelId = carModelId( car );
        id = car.getId();
        color = car.getColor();
        available = car.isAvailable();
        price = car.getPrice();

        CarDto carDto = new CarDto( id, modelId, color, available, price );

        return carDto;
    }

    private UUID carModelId(Car car) {
        if ( car == null ) {
            return null;
        }
        CarModel model = car.getModel();
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
