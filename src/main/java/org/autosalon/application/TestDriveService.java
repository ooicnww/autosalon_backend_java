package org.autosalon.application;

import org.autosalon.domain.exceptions.EntityNotFoundException;
import org.autosalon.domain.model.entities.car.Car;
import org.autosalon.domain.model.entities.testDrive.TestRequest;
import org.autosalon.domain.model.users.Client;
import org.autosalon.domain.model.users.User;
import org.autosalon.domain.repositories.ICarRepository;
import org.autosalon.domain.repositories.ITestRequestRepository;
import org.autosalon.domain.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TestDriveService {

    private final ITestRequestRepository repository;
    private final IUserRepository userRepository;
    private final ICarRepository carRepository;

    public TestDriveService(ITestRequestRepository repository, IUserRepository userRepository, ICarRepository carRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    public TestRequest createTestRequest(UUID clientId, UUID carId, LocalDateTime dateTime){

        User user = userRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Юзер не найден"));

        if (!(user instanceof Client client)) {
            throw new RuntimeException("Это не клиент");
        }

        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Машина не найдена"));

        TestRequest request = new TestRequest(client, car, dateTime);
        repository.save(request);

        return request;
    }

    public List<TestRequest> getAllRequests(){
        return repository.findAll();
    }

    public TestRequest getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Заявка на тест не найдена"));
    }

    public void delete(UUID id) {
        repository.delete(id);
    }
}