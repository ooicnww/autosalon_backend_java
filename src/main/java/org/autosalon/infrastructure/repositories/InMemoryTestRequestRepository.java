package org.autosalon.infrastructure.repositories;

import org.autosalon.domain.model.entities.car.Car;
import org.autosalon.domain.model.entities.car.Part;
import org.autosalon.domain.model.entities.order.Order;
import org.autosalon.domain.model.entities.testDrive.TestRequest;
import org.autosalon.domain.repositories.IOrderRepository;
import org.autosalon.domain.repositories.IPartRepository;
import org.autosalon.domain.repositories.ITestRequestRepository;

import java.util.*;

public class InMemoryTestRequestRepository extends InMemoryRepository<TestRequest> implements ITestRequestRepository {
    @Override
    public void save(TestRequest request){
        super.save(request.getId(), request);
    }

    @Override
    public Optional<TestRequest> findById(UUID id){
        return super.findById(id);
    }

    @Override
    public List<TestRequest> findAll(){
        return super.findAll();
    }

    @Override
    public void delete(UUID id){
        super.delete(id);
    }
}