package org.autosalon.mapper.mapperDto;

import java.util.Map;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.autosalon.domain.model.entities.car.CarComponentType;
import org.autosalon.domain.model.entities.car.CarModel;
import org.autosalon.domain.model.enums.BodyType;
import org.autosalon.domain.model.enums.DriveType;
import org.autosalon.domain.model.enums.FuelType;
import org.autosalon.domain.model.enums.TransmissionType;
import org.autosalon.presentation.dto.CarComponentDto;
import org.autosalon.presentation.dto.CarModelResponseDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-03T00:42:28+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.jar, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class CarModelResponseDtoMapperImpl implements CarModelResponseDtoMapper {

    @Override
    public CarModelResponseDto toDto(CarModel model) {
        if ( model == null ) {
            return null;
        }

        UUID id = null;
        String brand = null;
        String modelName = null;
        BodyType bodyType = null;
        FuelType fuelType = null;
        TransmissionType transmissionType = null;
        DriveType driveType = null;
        int basePrice = 0;
        int enginePower = 0;
        int engineCapacity = 0;

        id = model.getId();
        brand = model.getBrand();
        modelName = model.getModelName();
        bodyType = model.getBodyType();
        fuelType = model.getFuelType();
        transmissionType = model.getTransmissionType();
        driveType = model.getDriveType();
        basePrice = model.getBasePrice();
        enginePower = model.getEnginePower();
        engineCapacity = model.getEngineCapacity();

        Map<CarComponentType, CarComponentDto> defaultComponents = mapComponents(model);

        CarModelResponseDto carModelResponseDto = new CarModelResponseDto( id, brand, modelName, bodyType, fuelType, transmissionType, driveType, basePrice, defaultComponents, enginePower, engineCapacity );

        return carModelResponseDto;
    }
}
