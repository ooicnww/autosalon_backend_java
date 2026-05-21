package org.autosalon;

import org.autosalon.domain.model.users.UserType;
import org.autosalon.presentation.dto.UserRequestDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerIT extends BaseIT {

    @Test
    void createUser() {
        var dto = new UserRequestDto("Ivan", "ivan@test.com", UserType.CLIENT);

        var response = restTemplate.postForEntity("/users", dto, String.class);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void getUsers() {
        var response = restTemplate.getForEntity("/users", String.class);
        assertEquals(200, response.getStatusCode().value());
    }
}