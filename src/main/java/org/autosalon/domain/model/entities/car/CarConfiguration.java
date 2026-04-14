package org.autosalon.domain.model.entities.car;

import java.util.Map;
import java.util.UUID;

public record CarConfiguration(
        UUID id,
        CarModel model,
        Map<CarComponentType, CarComponent> components
) {
    public int getFullPrice() {
        return model.getBasePrice() + components.values().stream().mapToInt(CarComponent::getPrice).sum();
    }
}