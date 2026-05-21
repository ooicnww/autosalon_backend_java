package org.autosalon.infrastructure.client;

import org.autosalon.infrastructure.client.dto.CarResponse;
import org.autosalon.infrastructure.client.dto.ConfigurationResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Service
public class StorageClient {

    private final RestClient restClient;

    public StorageClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public CarResponse getCar(UUID carId) {

        return restClient.get()
                .uri("http://localhost:8083/cars/" + carId)
                .header("Authorization", getAuthHeader())
                .retrieve()
                .body(CarResponse.class);
    }

    public ConfigurationResponse getConfiguration(UUID configurationId) {

        return restClient.get()
                .uri("http://localhost:8083/configurations/" + configurationId)
                .header("Authorization", getAuthHeader())
                .retrieve()
                .body(ConfigurationResponse.class);
    }

    private String getAuthHeader() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return "Bearer " + jwt.getTokenValue();
    }
}