package org.autosalon.persistence.entityJpa.car;

import jakarta.persistence.*;
import org.autosalon.domain.model.entities.car.CarComponentType;

@Entity
@Table(name = "car_model_default_components", uniqueConstraints = @UniqueConstraint(columnNames = {"model_id", "type"}))
public class CarModelDefaultComponentsJpa extends BaseJpa {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", nullable = false)
    private CarModelJpa model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_id", nullable = false)
    private CarComponentJpa component;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarComponentType type;

    public CarModelJpa getModel() {
        return model;
    }

    public void setModel(CarModelJpa model) {
        this.model = model;
    }

    public CarComponentJpa getComponent() {
        return component;
    }

    public void setComponent(CarComponentJpa component) {
        this.component = component;
    }

    public CarComponentType getType() {
        return type;
    }

    public void setType(CarComponentType type) {
        this.type = type;
    }
}