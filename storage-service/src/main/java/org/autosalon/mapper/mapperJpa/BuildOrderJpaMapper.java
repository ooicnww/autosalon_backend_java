package org.autosalon.mapper.mapperJpa;

import org.autosalon.domain.build.BuildOrder;
import org.autosalon.persistence.entityJpa.build.BuildOrderJpa;
import org.springframework.stereotype.Component;

@Component
public class BuildOrderJpaMapper {

    public BuildOrderJpa toJpa(BuildOrder buildOrder) {

        BuildOrderJpa jpa = new BuildOrderJpa();

        jpa.setId(buildOrder.getId());
        jpa.setSourceOrderId(buildOrder.getSourceOrderId());
        jpa.setCreatedAt(buildOrder.getCreatedAt());
        jpa.setUpdatedAt(buildOrder.getUpdatedAt());
        jpa.setStatus(buildOrder.getStatus());
        jpa.setRemoved(buildOrder.isRemoved());

        return jpa;
    }

    public BuildOrder toDomain(BuildOrderJpa jpa) {

        return new BuildOrder(
                jpa.getId(),
                jpa.getSourceOrderId(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt(),
                jpa.getStatus(),
                jpa.isRemoved()
        );
    }
}