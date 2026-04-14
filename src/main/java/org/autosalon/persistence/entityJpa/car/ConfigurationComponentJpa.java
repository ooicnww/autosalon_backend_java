package org.autosalon.persistence.entityJpa.car;
import jakarta.persistence.*;
import org.autosalon.domain.model.entities.car.CarComponentType;

@Entity
@Table(
        name = "configuration_components",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"configuration_id", "type"}
        )
)

public class ConfigurationComponentJpa extends BaseJpa {

    @ManyToOne
    @JoinColumn(name = "configuration_id")
    private CarConfigurationJpa configuration;

    @ManyToOne
    @JoinColumn(name = "component_id")
    private CarComponentJpa component;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarComponentType type;

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

    public CarConfigurationJpa getConfiguration() {
        return configuration;
    }

    public void setConfiguration(CarConfigurationJpa configuration) {
        this.configuration = configuration;
    }
}