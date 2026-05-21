package org.autosalon.mapper.mapperJpa;

import org.autosalon.domain.exceptions.DomainValidationException;
import org.autosalon.domain.model.entities.testDrive.TestRequest;
import org.autosalon.domain.model.users.Client;
import org.autosalon.domain.model.users.User;
import org.autosalon.persistence.entityJpa.testRequest.TestRequestJpa;
import org.autosalon.persistence.entityJpa.user.UserJpa;
import org.springframework.stereotype.Component;

@Component
public class TestRequestJpaMapper {

    private final UserJpaMapper userMapper;

    public TestRequestJpaMapper(UserJpaMapper userMapper) {
        this.userMapper = userMapper;
    }

    public TestRequest toDomain(TestRequestJpa jpa) {

        User user = userMapper.toDomain(jpa.getClient());

        if (!(user instanceof Client client)) {
            throw new DomainValidationException("User is not a client");
        }

        return new TestRequest(
                jpa.getId(),
                client,
                jpa.getCarId(),
                jpa.getDateTime()
        );
    }

    public TestRequestJpa toJpa(TestRequest domain) {

        TestRequestJpa jpa = new TestRequestJpa();

        jpa.setId(domain.getId());
        jpa.setDateTime(domain.getDateTime());

        UserJpa client = new UserJpa();
        client.setId(domain.getClient().getId());

        jpa.setClient(client);
        jpa.setCarId(domain.getCarId());

        return jpa;
    }
}