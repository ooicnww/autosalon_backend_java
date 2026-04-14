package org.autosalon.presentation.controller;

import org.autosalon.application.UserService;
import org.autosalon.domain.model.users.User;
import org.autosalon.mapper.mapperDto.UserResponseDtoMapper;
import org.autosalon.presentation.dto.UserRequestDto;
import org.autosalon.presentation.dto.UserResponseDto;
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

    @PostMapping
    public void create(@RequestBody UserRequestDto dto) {
        userService.createUser(dto);
    }

    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.getAll().stream().map(mapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable UUID id) {
        User user = userService.getById(id);
        return mapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userService.delete(id);
    }
}