package org.autosalon.application;

import org.autosalon.domain.build.BuildOrder;
import org.autosalon.domain.build.BuildOrderStatus;
import org.autosalon.domain.repositories.IBuildOrderRepository;
import org.autosalon.presentation.dto.BuildOrderRequestDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class BuildOrderService {

    private final IBuildOrderRepository repository;

    public BuildOrderService(IBuildOrderRepository repository) {
        this.repository = repository;
    }

    public BuildOrder create(BuildOrderRequestDto dto) {

        BuildOrder buildOrder = new BuildOrder(
                UUID.randomUUID(),
                dto.sourceOrderId(),
                Instant.now(),
                Instant.now(),
                BuildOrderStatus.CREATED,
                false
        );

        return repository.save(buildOrder);
    }

    public List<BuildOrder> getAll() {
        return repository.findAll();
    }

    public BuildOrder getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Запрос на заказ не найден"));
    }

    public void delete(UUID id) {
        repository.delete(id);
    }
}