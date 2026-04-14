package org.autosalon.persistence.specification;

import org.autosalon.persistence.entityJpa.car.CarModelJpa;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class CarModelSpecification {

    public static Specification<CarModelJpa> withBrand(String brand) {
        return (model, query, builder) -> brand == null ? null : builder.equal(model.get("brand"), brand);
    }

    public static Specification<CarModelJpa> withComponent(UUID componentId) {
        return (model, query, builder) -> componentId == null ? null : builder.equal(model.join("defaultComponents").join("component").get("id"), componentId);
    }

}
