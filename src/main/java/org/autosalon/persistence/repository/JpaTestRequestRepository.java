package org.autosalon.persistence.repository;

import org.autosalon.domain.model.entities.testDrive.TestRequest;
import org.autosalon.domain.repositories.ITestRequestRepository;
import org.autosalon.mapper.mapperJpa.TestRequestJpaMapper;
import org.autosalon.persistence.entityJpa.car.CarJpa;
import org.autosalon.persistence.entityJpa.testRequest.TestRequestJpa;
import org.autosalon.persistence.entityJpa.user.UserJpa;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class JpaTestRequestRepository implements ITestRequestRepository {

    private final TestRequestJpaRepository jpaRepository;
    private final TestRequestJpaMapper mapper;

    public JpaTestRequestRepository(TestRequestJpaRepository jpaRepository, TestRequestJpaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(TestRequest request) {
        TestRequestJpa jpa = new TestRequestJpa();

        jpa.setId(request.getId());
        jpa.setDateTime(request.getDateTime());

        UserJpa client = new UserJpa();
        client.setId(request.getClient().getId());
        jpa.setClient(client);

        CarJpa car = new CarJpa();
        car.setId(request.getCar().getId());
        jpa.setCar(car);

        jpaRepository.save(jpa);
    }


    @Override
    public List<TestRequest> findAll() {
        return jpaRepository.findAll().stream().filter(jpa -> !jpa.isRemoved()).map(mapper::toDomain).toList();
    }

    @Override
    public Optional<TestRequest> findById(UUID id) {
        return jpaRepository.findById(id).filter(jpa -> !jpa.isRemoved()).map(mapper::toDomain);
    }

    @Override
    public void delete(UUID id) {
        TestRequestJpa jpa = jpaRepository.findById(id).orElseThrow();

        jpa.setRemoved(true);
        jpaRepository.save(jpa);
    }
}