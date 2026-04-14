package org.autosalon.presentation.controller;

import org.autosalon.application.CarComponentService;
import org.autosalon.mapper.mapperDto.CarComponentDtoMapper;
import org.autosalon.presentation.dto.CarComponentDto;
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

    @PostMapping
    public void create(@RequestBody CarComponentDto dto) {
        componentService.createComponent(dto);
    }

    @GetMapping
    public List<CarComponentDto> getAll() {
        return componentService.getAll().stream().map(mapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public CarComponentDto getById(@PathVariable UUID id) {
        return mapper.toDto(componentService.getById(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        componentService.delete(id);
    }

    @PutMapping("/{id}/models")
    public void setModels(
            @PathVariable UUID id,
            @RequestBody Set<UUID> modelIds
    ) {
        componentService.setSuitableModels(id, modelIds);
    }
}