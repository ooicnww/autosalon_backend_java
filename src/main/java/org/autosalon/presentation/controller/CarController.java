package org.autosalon.presentation.controller;

import org.autosalon.application.CarService;
import org.autosalon.domain.model.entities.car.Car;
import org.autosalon.domain.model.entities.car.CarModel;
import org.autosalon.presentation.dto.CarDto;
import org.autosalon.mapper.mapperDto.CarDtoMapper;
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
    @GetMapping
    public List<CarDto> getAllCars() {
        return carService.getAllCars().stream().map(mapper::toDto).toList();
    }

    @GetMapping("/id")
    public CarDto getCarById(@PathVariable UUID id){
        return carService.getCarById(id).map(mapper::toDto).orElseThrow(() -> new RuntimeException("Машина не найдена"));
    }

    @PostMapping
    public void createCar(@RequestBody CarDto dto) {
        carService.addCar(dto);
    }

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

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable UUID id) {
        carService.deleteCar(id);
    }

}