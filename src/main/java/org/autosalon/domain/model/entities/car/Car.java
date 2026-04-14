package org.autosalon.domain.model.entities.car;

import java.util.UUID;

public class Car{
    private UUID id;
    private CarModel model;
    private String color;
    private boolean isAvailable;
    private int price;

    public Car(CarModel model, String color, boolean isAvailable, int price){
        this.id = UUID.randomUUID();
        this.model = model;
        this.color = color;
        this.isAvailable = isAvailable;
        this.price = price;
    }

    public Car(UUID id, CarModel model, String color, boolean isAvailable, int price) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.isAvailable = isAvailable;
        this.price = price;
    }


    public UUID getId() {
        return id;
    }

    public CarModel getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
    public int getPrice() {
        return price;
    }

    public void resetAvailable(){
        boolean res = !isAvailable;
        isAvailable = res;
    }

}