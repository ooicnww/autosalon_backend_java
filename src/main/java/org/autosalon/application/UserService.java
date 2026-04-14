package org.autosalon.application;

import org.autosalon.domain.model.users.*;
import org.autosalon.domain.repositories.IUserRepository;
import org.autosalon.mapper.mapperDto.UserRequestDtoMapper;
import org.autosalon.presentation.dto.UserRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final IUserRepository userRepository;
    private final UserRequestDtoMapper mapper;

    public UserService(IUserRepository userRepository, UserRequestDtoMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public User createUser(UserRequestDto dto) {
        User user = mapper.toDomain(dto);
        userRepository.save(user);
        return user;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    public void delete(UUID id) {
        userRepository.delete(id);
    }
}