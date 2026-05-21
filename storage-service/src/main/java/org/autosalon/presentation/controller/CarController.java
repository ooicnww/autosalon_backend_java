package org.autosalon.presentation.controller;

import org.autosalon.application.CarService;
import org.autosalon.domain.model.entities.car.Car;
import org.autosalon.domain.model.entities.car.CarModel;
import org.autosalon.presentation.dto.CarDto;
import org.autosalon.mapper.mapperDto.CarDtoMapper;
import org.autosalon.presentation.dto.CarResponseDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/cars")
public class CarController {

    private final CarService carService;
    private final CarDtoMapper mapper;

    public CarController(CarService carService, CarDtoMapper mapper) {
        this.carService = carService;
        this.mapper = mapper;
    }

    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @GetMapping
    public List<CarDto> getAllCars() {
        return carService.getAllCars().stream().map(mapper::toDto).toList();
    }

    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    @GetMapping("/{id}")
    public CarResponseDto getById(@PathVariable UUID id) {
        return carService.getById(id);
    }

    @PreAuthorize("hasAnyRole('WAREHOUSE_ADMIN','ADMIN')")
    @PostMapping
    public void createCar(@RequestBody CarDto dto) {
        carService.addCar(dto);
    }

    @PreAuthorize("hasAnyRole('WAREHOUSE_ADMIN','ADMIN')")
    @PutMapping("/{id}")
    public void updateCar(@PathVariable UUID id, @RequestBody CarDto dto) {

        CarDto updatedDto = new CarDto(
                id,
                dto.modelId(),
                dto.color(),
                dto.available(),
                dto.price()
        );

        carService.updateCar(updatedDto);
    }

    @PreAuthorize("hasAnyRole('WAREHOUSE_ADMIN','ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable UUID id) {
        carService.deleteCar(id);
    }


}