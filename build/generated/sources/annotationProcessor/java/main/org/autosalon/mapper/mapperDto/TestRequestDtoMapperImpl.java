package org.autosalon.mapper.mapperDto;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.autosalon.domain.model.entities.car.Car;
import org.autosalon.domain.model.entities.testDrive.TestRequest;
import org.autosalon.domain.model.users.Client;
import org.autosalon.presentation.dto.TestRequestResponseDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-14T15:43:24+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.jar, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class TestRequestDtoMapperImpl implements TestRequestDtoMapper {

    @Override
    public TestRequestResponseDto toDto(TestRequest domain) {
        if ( domain == null ) {
            return null;
        }

        UUID clientId = null;
        UUID carId = null;
        UUID id = null;
        LocalDateTime dateTime = null;

        clientId = domainClientId( domain );
        carId = domainCarId( domain );
        id = domain.getId();
        dateTime = domain.getDateTime();

        TestRequestResponseDto testRequestResponseDto = new TestRequestResponseDto( id, clientId, carId, dateTime );

        return testRequestResponseDto;
    }

    private UUID domainClientId(TestRequest testRequest) {
        if ( testRequest == null ) {
            return null;
        }
        Client client = testRequest.getClient();
        if ( client == null ) {
            return null;
        }
        UUID id = client.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private UUID domainCarId(TestRequest testRequest) {
        if ( testRequest == null ) {
            return null;
        }
        Car car = testRequest.getCar();
        if ( car == null ) {
            return null;
        }
        UUID id = car.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
