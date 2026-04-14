package org.autosalon.domain.model.entities.car;

import org.autosalon.domain.valueObjects.ModelKey;

import java.util.Set;
import java.util.UUID;

public class CarComponent {

    private UUID id;
    private CarComponentType type;
    private String name;
    private int price;
    private Set<UUID> suitableModels;

    public CarComponent(
            CarComponentType type,
            String name,
            int price,
            Set<UUID> suitableModels
    ) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.name = name;
        this.price = price;
        this.suitableModels = suitableModels;
    }

    public CarComponent(
            UUID id,
            CarComponentType type,
            String name,
            int price,
            Set<UUID> suitableModels
    ) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
        this.suitableModels = suitableModels;
    }

    public UUID getId() {
        return id;
    }

    public CarComponentType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Set<UUID> getSuitableModels() {
        return suitableModels;
    }
}