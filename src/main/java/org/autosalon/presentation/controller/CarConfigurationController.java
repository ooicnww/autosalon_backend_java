package org.autosalon.presentation.controller;

import org.autosalon.application.CarComponentService;
import org.autosalon.application.CarConfigurationApplicationService;
import org.autosalon.mapper.mapperDto.CarComponentDtoMapper;
import org.autosalon.mapper.mapperDto.CarConfigurationResponseDtoMapper;
import org.autosalon.presentation.dto.CarComponentDto;
import org.autosalon.presentation.dto.CarConfigurationRequestDto;
import org.autosalon.presentation.dto.CarConfigurationResponseDto;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.Set;
import java.util.UUID;
@RestController
@RequestMapping("/configurations")
public class CarConfigurationController {

    private final CarConfigurationApplicationService carConfigurationApplicationService;
    private final CarConfigurationResponseDtoMapper carConfigurationResponseDtoMapper;

    public CarConfigurationController(CarConfigurationApplicationService carConfigurationApplicationService, CarConfigurationResponseDtoMapper carConfigurationResponseDtoMapper) {
        this.carConfigurationApplicationService = carConfigurationApplicationService;
        this.carConfigurationResponseDtoMapper = carConfigurationResponseDtoMapper;
    }

    @PostMapping
    public void create(@RequestBody CarConfigurationRequestDto dto) {
        carConfigurationApplicationService.createConfiguration(dto);
    }

    @GetMapping
    public List<CarConfigurationResponseDto> getAll() {
        return carConfigurationApplicationService.getAllConfigurations().stream().map(carConfigurationResponseDtoMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public CarConfigurationResponseDto getById(@PathVariable UUID id) {
        return carConfigurationResponseDtoMapper.toDto(carConfigurationApplicationService.getById(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        carConfigurationApplicationService.delete(id);
    }

}