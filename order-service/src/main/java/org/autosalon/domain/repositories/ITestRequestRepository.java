package org.autosalon.domain.repositories;

import org.autosalon.domain.model.entities.testDrive.TestRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITestRequestRepository{
    void save(TestRequest request);

    Optional<TestRequest> findById(UUID id);

    List<TestRequest> findAll();

    void delete(UUID id);
}