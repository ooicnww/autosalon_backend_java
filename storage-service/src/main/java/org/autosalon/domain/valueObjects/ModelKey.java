package org.autosalon.domain.valueObjects;

public class ModelKey{
    private String brand;
    private String model;

    public ModelKey(String brand, String model){
        this.brand = brand;
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }
}