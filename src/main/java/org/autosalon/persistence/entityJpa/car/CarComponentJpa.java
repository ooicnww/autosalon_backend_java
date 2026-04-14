package org.autosalon.persistence.entityJpa.car;

import jakarta.persistence.*;
import org.autosalon.domain.model.entities.car.CarComponentType;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "car_components")
public class CarComponentJpa extends BaseJpa {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarComponentType type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @ManyToMany
    @JoinTable(
            name = "component_model",
            joinColumns = @JoinColumn(name = "component_id"),
            inverseJoinColumns = @JoinColumn(name = "model_id")
    )
    private Set<CarModelJpa> suitableModels = new HashSet<>();

    public CarComponentJpa() {}

    public CarComponentJpa(CarComponentType type, String name, int price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public CarComponentType getType() {
        return type;
    }

    public void setType(CarComponentType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Set<CarModelJpa> getSuitableModels() {
        return suitableModels;
    }

    public void setSuitableModels(Set<CarModelJpa> suitableModels) {
        this.suitableModels = suitableModels;
    }
}