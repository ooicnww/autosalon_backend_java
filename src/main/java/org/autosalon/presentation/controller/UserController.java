package org.autosalon.presentation.controller;

import org.autosalon.application.UserService;
import org.autosalon.domain.model.users.User;
import org.autosalon.mapper.mapperDto.UserResponseDtoMapper;
import org.autosalon.presentation.dto.UserRequestDto;
import org.autosalon.presentation.dto.UserResponseDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserResponseDtoMapper mapper;

    public UserController(UserService userService, UserResponseDtoMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void create(@RequestBody UserRequestDto dto) {
        userService.createUser(dto);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/me")
    public void createMe() {
        userService.createThisUser();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.getAll().stream().map(mapper::toDto).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable UUID id) {
        User user = userService.getById(id);
        return mapper.toDto(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userService.delete(id);
    }
}