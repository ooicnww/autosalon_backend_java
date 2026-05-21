package org.autosalon.presentation.controller;

import org.autosalon.application.TestDriveService;
import org.autosalon.domain.model.entities.testDrive.TestRequest;
import org.autosalon.mapper.mapperDto.TestRequestDtoMapper;
import org.autosalon.presentation.dto.TestRequestRequestDto;
import org.autosalon.presentation.dto.TestRequestResponseDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/test-drives")
public class TestRequestController {

    private final TestDriveService service;
    private final TestRequestDtoMapper mapper;

    public TestRequestController(TestDriveService service, TestRequestDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public TestRequestResponseDto create(@RequestBody TestRequestRequestDto dto) {

        TestRequest request = service.createTestRequest(
                dto.carId(),
                dto.dateTime()
        );

        return mapper.toDto(request);
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @GetMapping
    public List<TestRequestResponseDto> getAll() {
        return service.getAllRequests().stream().map(mapper::toDto).toList();
    }


    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @GetMapping("/{id}")
    public TestRequestResponseDto getById(@PathVariable UUID id) {
        TestRequest request = service.getById(id);
        return mapper.toDto(request);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}