package org.autosalon.persistence.entityJpa.car;

import jakarta.persistence.*;


@Entity
@Table(name = "cars")
public class CarJpa extends BaseJpa {

    @ManyToOne
    @JoinColumn(name = "model_id")
    private CarModelJpa model;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private boolean available;

    @Column(nullable = false)
    private int price;


    public CarJpa() {}

    public CarJpa(CarModelJpa model, String color, boolean available, int price) {
        this.model = model;
        this.color = color;
        this.available = available;
        this.price = price;
    }


    public CarModelJpa getModel() {
        return model;
    }

    public void setModel(CarModelJpa model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}