package org.autosalon.presentation.controller;

import org.autosalon.application.BuildOrderService;
import org.autosalon.domain.build.BuildOrder;
import org.autosalon.presentation.dto.BuildOrderRequestDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/build-orders")
public class BuildOrderController {

    private final BuildOrderService service;

    public BuildOrderController(BuildOrderService service) {
        this.service = service;
    }

    @PostMapping
    public BuildOrder create(@RequestBody BuildOrderRequestDto dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<BuildOrder> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public BuildOrder getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}