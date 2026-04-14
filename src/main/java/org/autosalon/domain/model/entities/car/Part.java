package org.autosalon.domain.model.entities.car;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public class Part{
    private UUID id;
    private String name;
    private int price;
    private Set<CarModel> suitableModels;

    public Part(String name, int price, Set<CarModel> suitableModels) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.suitableModels = suitableModels;
    }

    public UUID getId() {
        return id;
    }
    public String getName(){
        return name;
    }
    public int getPrice(){
        return price;
    }
    public Set<CarModel> getSuitableModels(){
        return suitableModels;
    }
}