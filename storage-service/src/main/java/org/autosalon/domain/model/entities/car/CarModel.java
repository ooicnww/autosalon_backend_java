package org.autosalon.domain.model.entities.car;

import org.autosalon.domain.model.enums.BodyType;
import org.autosalon.domain.model.enums.DriveType;
import org.autosalon.domain.model.enums.FuelType;
import org.autosalon.domain.model.enums.TransmissionType;

import java.util.Map;
import java.util.UUID;

public class CarModel{
    private UUID id;
    private String brand;
    private String modelName;
    private BodyType bodyType;
    private FuelType fuelType;
    private TransmissionType transmissionType;
    private DriveType driveType;
    private int basePrice;
    private Map<CarComponentType, CarComponent> defaultComponents;
    private int enginePower;
    private int engineCapacity;

    public CarModel(
            String brand,
            String modelName,
            BodyType bodyType,
            FuelType fuelType,
            TransmissionType transmissionType,
            DriveType driveType,
            int basePrice,
            Map<CarComponentType, CarComponent> defaultComponents,
            int enginePower,
            int engineCapacity) {

        this.id = UUID.randomUUID();
        this.brand = brand;
        this.modelName = modelName;
        this.bodyType = bodyType;
        this.fuelType = fuelType;
        this.transmissionType = transmissionType;
        this.driveType = driveType;
        this.basePrice = basePrice;
        this.defaultComponents = defaultComponents;
        this.engineCapacity = engineCapacity;
        this.enginePower = enginePower;
    }

    public CarModel(
            UUID id,
            String brand,
            String modelName,
            BodyType bodyType,
            FuelType fuelType,
            TransmissionType transmissionType,
            DriveType driveType,
            int basePrice,
            Map<CarComponentType, CarComponent> defaultComponents,
            int enginePower,
            int engineCapacity
    ) {
        this.id = id;
        this.brand = brand;
        this.modelName = modelName;
        this.bodyType = bodyType;
        this.fuelType = fuelType;
        this.transmissionType = transmissionType;
        this.driveType = driveType;
        this.basePrice = basePrice;
        this.defaultComponents = defaultComponents;
        this.engineCapacity = engineCapacity;
        this.enginePower = enginePower;
    }

    public Map<CarComponentType, CarComponent> getDefaultComponents(){
        return defaultComponents;
    }

    public int getBasePrice(){
        return basePrice;
    }

    public String getBrand() {
        return brand;
    }

    public String getModelName() {
        return modelName;
    }

    public BodyType getBodyType() {
        return bodyType;
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

    public DriveType getDriveType() {
        return driveType;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public UUID getId() {
        return id;
    }

}