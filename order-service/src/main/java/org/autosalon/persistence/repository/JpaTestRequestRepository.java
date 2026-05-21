package org.autosalon.persistence.repository;

import org.autosalon.domain.model.entities.testDrive.TestRequest;
import org.autosalon.domain.model.users.Client;
import org.autosalon.domain.repositories.ITestRequestRepository;
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

    public JpaTestRequestRepository(TestRequestJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(TestRequest request) {

        TestRequestJpa jpa = new TestRequestJpa();

        jpa.setId(request.getId());
        jpa.setDateTime(request.getDateTime());

        UserJpa client = new UserJpa();
        client.setId(request.getClient().getId());

        jpa.setClient(client);
        jpa.setCarId(request.getCarId());

        jpaRepository.save(jpa);
    }

    @Override
    public List<TestRequest> findAll() {

        return jpaRepository.findAll()
                .stream()
                .filter(jpa -> !jpa.isRemoved())
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<TestRequest> findById(UUID id) {

        return jpaRepository.findById(id)
                .filter(jpa -> !jpa.isRemoved())
                .map(this::toDomain);
    }

    @Override
    public void delete(UUID id) {

        TestRequestJpa jpa = jpaRepository.findById(id)
                .orElseThrow();

        jpa.setRemoved(true);

        jpaRepository.save(jpa);
    }

    private TestRequest toDomain(TestRequestJpa jpa) {

        Client client = new Client(
                jpa.getClient().getId(),
                jpa.getClient().getName(),
                jpa.getClient().getEmail()
        );

        return new TestRequest(
                jpa.getId(),
                client,
                jpa.getCarId(),
                jpa.getDateTime()
        );
    }
}