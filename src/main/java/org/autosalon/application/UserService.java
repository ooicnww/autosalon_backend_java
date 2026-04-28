package org.autosalon.application;

import org.autosalon.config.SecurityUtils;
import org.autosalon.domain.model.users.*;
import org.autosalon.domain.repositories.IUserRepository;
import org.autosalon.mapper.mapperDto.UserRequestDtoMapper;
import org.autosalon.presentation.dto.UserRequestDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
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

    public User createThisUser() {
        UUID userId = SecurityUtils.getCurrentUserId();

        var auth = SecurityContextHolder.getContext().getAuthentication();
        var jwt = (Jwt) auth.getPrincipal();

        String name = jwt.getClaim("preferred_username");
        String email = jwt.getClaim("email");

        return userRepository.findById(userId)
                .orElseGet(() -> {

                    if (SecurityUtils.hasRole("USER")) {
                        Client client = new Client(userId, name, email);
                        userRepository.save(client);
                        return client;
                    }

                    else if (SecurityUtils.hasRole("MANAGER")) {
                        Manager manager = new Manager(userId, name, email);
                        userRepository.save(manager);
                        return manager;
                    }

                    else if (SecurityUtils.hasRole("ADMIN")) {
                        SysAdmin admin = new SysAdmin(userId, name, email);
                        userRepository.save(admin);
                        return admin;
                    }

                    else if (SecurityUtils.hasRole("WAREHOUSE_ADMIN")) {
                        WareAdmin admin = new WareAdmin(userId, name, email);
                        userRepository.save(admin);
                        return admin;
                    }

                    throw new RuntimeException("Неизвестная роль");
                });
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    public void delete(UUID id) {
        userRepository.delete(id);
    }
}