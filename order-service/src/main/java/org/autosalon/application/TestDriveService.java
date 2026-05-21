package org.autosalon.application;

import org.autosalon.config.SecurityUtils;
import org.autosalon.domain.exceptions.EntityNotFoundException;
import org.autosalon.domain.exceptions.LockedActionException;
import org.autosalon.domain.model.entities.testDrive.TestRequest;
import org.autosalon.domain.model.users.Client;
import org.autosalon.domain.model.users.User;
import org.autosalon.domain.repositories.ITestRequestRepository;
import org.autosalon.domain.repositories.IUserRepository;
import org.autosalon.infrastructure.client.StorageClient;
import org.autosalon.infrastructure.client.dto.CarResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TestDriveService {

    private final ITestRequestRepository repository;
    private final IUserRepository userRepository;
    private final StorageClient storageClient;

    public TestDriveService(
            ITestRequestRepository repository,
            IUserRepository userRepository,
            StorageClient storageClient
    ) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.storageClient = storageClient;
    }

    private void checkOwner(TestRequest request) {

        if (SecurityUtils.hasRole("ADMIN")) {
            return;
        }

        UUID currentUserId = SecurityUtils.getCurrentUserId();

        if (!request.getClient().getId().equals(currentUserId)) {
            throw new RuntimeException("Доступ запрещен");
        }
    }

    public TestRequest createTestRequest(UUID carId, LocalDateTime dateTime) {

        UUID clientId = SecurityUtils.getCurrentUserId();

        User user = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Юзер не найден"));

        if (!(user instanceof Client client)) {
            throw new RuntimeException("Это не клиент");
        }

        CarResponse car = storageClient.getCar(carId);

        if (!car.available()) {
            throw new LockedActionException("Машина недоступна");
        }

        TestRequest request = new TestRequest(
                client,
                car.id(),
                dateTime
        );

        repository.save(request);

        return request;
    }

    public List<TestRequest> getAllRequests() {
        return repository.findAll();
    }

    public TestRequest getById(UUID id) {

        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Заявка на тест не найдена"));
    }

    public void delete(UUID id) {

        TestRequest request = repository.findById(id).orElseThrow(() -> new RuntimeException("Не найдено"));

        checkOwner(request);

        repository.delete(id);
    }
}