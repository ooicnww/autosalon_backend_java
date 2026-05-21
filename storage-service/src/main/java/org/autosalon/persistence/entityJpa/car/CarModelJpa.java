package org.autosalon.persistence.entityJpa.car;

import jakarta.persistence.*;
import org.autosalon.domain.model.enums.BodyType;
import org.autosalon.domain.model.enums.DriveType;
import org.autosalon.domain.model.enums.FuelType;
import org.autosalon.domain.model.enums.TransmissionType;
import org.autosalon.persistence.entityJpa.BaseJpa;

import java.util.Set;

@Entity
@Table(name = "car_models")
public class CarModelJpa extends BaseJpa {
    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String modelName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BodyType bodyType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransmissionType transmissionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DriveType driveType;

    @Column(nullable = false)
    private int basePrice;

    @Column(nullable = false)
    private int enginePower;

    @Column(nullable = false)
    private int engineCapacity;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<CarModelDefaultComponentsJpa> defaultComponents;

    public CarModelJpa() {}

    public CarModelJpa(String brand, String modelName, BodyType bodyType, FuelType fuelType, TransmissionType transmissionType, DriveType driveType, int basePrice, int enginePower, int engineCapacity)
    {
        this.brand = brand;
        this.modelName = modelName;
        this.bodyType = bodyType;
        this.fuelType = fuelType;
        this.transmissionType = transmissionType;
        this.driveType = driveType;
        this.basePrice = basePrice;
        this.enginePower = enginePower;
        this.engineCapacity = engineCapacity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public TransmissionType getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(TransmissionType transmissionType) {
        this.transmissionType = transmissionType;
    }

    public DriveType getDriveType() {
        return driveType;
    }

    public void setDriveType(DriveType driveType) {
        this.driveType = driveType;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(int enginePower) {
        this.enginePower = enginePower;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public Set<CarModelDefaultComponentsJpa> getDefaultComponents() {
        return defaultComponents;
    }

    public void setDefaultComponents(Set<CarModelDefaultComponentsJpa> defaultComponents) {
        this.defaultComponents = defaultComponents;
    }
}