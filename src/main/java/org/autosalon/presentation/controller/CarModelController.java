package org.autosalon.presentation.controller;

import org.autosalon.application.CarModelService;
import org.autosalon.mapper.mapperDto.CarModelResponseDtoMapper;
import org.autosalon.presentation.dto.CarModelRequestDto;
import org.autosalon.presentation.dto.CarModelResponseDto;
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

    @PostMapping
    public void createModel(@RequestBody CarModelRequestDto dto) {
        modelService.createModel(dto);
    }

    @GetMapping
    public List<CarModelResponseDto> getAllModels(@RequestParam(required = false) String brand, @RequestParam(required = false) UUID componentId)
    {
        return modelService.getAllModels(brand, componentId).stream().map(mapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public CarModelResponseDto getModel(@PathVariable UUID id) {
        return mapper.toDto(modelService.getModelById(id));
    }

    @PutMapping("/{id}")
    public void updateModel(@PathVariable UUID id, @RequestBody CarModelRequestDto dto) {
        modelService.updateModel(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteModel(@PathVariable UUID id) {
        modelService.deleteModel(id);
    }

}