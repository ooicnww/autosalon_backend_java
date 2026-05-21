package org.autosalon.presentation.controller;

import org.autosalon.application.CarModelService;
import org.autosalon.mapper.mapperDto.CarModelResponseDtoMapper;
import org.autosalon.presentation.dto.CarModelRequestDto;
import org.autosalon.presentation.dto.CarModelResponseDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/models")
public class CarModelController {

    private final CarModelService modelService;
    private final CarModelResponseDtoMapper mapper;

    public CarModelController(CarModelService modelService, CarModelResponseDtoMapper mapper) {
        this.modelService = modelService;
        this.mapper = mapper;
    }

    @PreAuthorize("hasAnyRole('WAREHOUSE_ADMIN','ADMIN')")
    @PostMapping
    public void createModel(@RequestBody CarModelRequestDto dto) {
        modelService.createModel(dto);
    }


    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @GetMapping
    public List<CarModelResponseDto> getAllModels(@RequestParam(required = false) String brand, @RequestParam(required = false) UUID componentId)
    {
        return modelService.getAllModels(brand, componentId).stream().map(mapper::toDto).toList();
    }

    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @GetMapping("/{id}")
    public CarModelResponseDto getModel(@PathVariable UUID id) {
        return mapper.toDto(modelService.getModelById(id));
    }

    @PreAuthorize("hasAnyRole('WAREHOUSE_ADMIN','ADMIN')")
    @PutMapping("/{id}")
    public void updateModel(@PathVariable UUID id, @RequestBody CarModelRequestDto dto) {
        modelService.updateModel(id, dto);
    }

    @PreAuthorize("hasAnyRole('WAREHOUSE_ADMIN','ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteModel(@PathVariable UUID id) {
        modelService.deleteModel(id);
    }

}