package org.autosalon.persistence.entityJpa.car;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "car_configurations")
public class CarConfigurationJpa extends BaseJpa {

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private CarModelJpa model;

    @OneToMany(mappedBy = "configuration", cascade = CascadeType.ALL)
    private Set<ConfigurationComponentJpa> components;

    @Column(nullable = false)
    private int totalPrice;

    public CarConfigurationJpa() {}

    public CarModelJpa getModel() {
        return model;
    }
    public void setModel(CarModelJpa model) {
        this.model = model;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Set<ConfigurationComponentJpa> getComponents() {
        return components;
    }

    public void setComponents(Set<ConfigurationComponentJpa> components) {
        this.components = components;
    }
}