package org.autosalon.presentation.controller;

import org.autosalon.application.CarComponentService;
import org.autosalon.mapper.mapperDto.CarComponentDtoMapper;
import org.autosalon.presentation.dto.CarComponentDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
@RestController
@RequestMapping("/components")
public class CarComponentController {

    private final CarComponentService componentService;
    private final CarComponentDtoMapper mapper;

    public CarComponentController(CarComponentService componentService, CarComponentDtoMapper mapper) {
        this.componentService = componentService;
        this.mapper = mapper;
    }

    @PreAuthorize("hasAnyRole('WAREHOUSE_ADMIN','ADMIN')")
    @PostMapping
    public void create(@RequestBody CarComponentDto dto) {
        componentService.createComponent(dto);
    }

    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @GetMapping
    public List<CarComponentDto> getAll() {
        return componentService.getAll().stream().map(mapper::toDto).toList();
    }

    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @GetMapping("/{id}")
    public CarComponentDto getById(@PathVariable UUID id) {
        return mapper.toDto(componentService.getById(id));
    }

    @PreAuthorize("hasAnyRole('WAREHOUSE_ADMIN','ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        componentService.delete(id);
    }

    @PreAuthorize("hasAnyRole('WAREHOUSE_ADMIN','ADMIN')")
    @PutMapping("/{id}/models")
    public void setModels(
            @PathVariable UUID id,
            @RequestBody Set<UUID> modelIds
    ) {
        componentService.setSuitableModels(id, modelIds);
    }
}