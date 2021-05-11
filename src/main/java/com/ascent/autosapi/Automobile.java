package com.ascent.autosapi;

import java.util.Random;

public class Automobile {
    private int id;
    private String make;
    private String color;
    private Long vin;

    public Automobile() {
    }

    public Automobile(int id) {
        this.id = id;
        this.color = "Color " + id;
        this.make = "Make " + id;
        this.vin = new Random().nextLong();
    }

    public Automobile(int id, String make, String color) {
        this.id = id;
        this.make = make;
        this.color = color;
        this.vin = new Random().nextLong();
    }


    @Override
    public String toString() {
        return "Automobile{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", color='" + color + '\'' +
                ", vin=" + vin +
                '}';
    }

    public int getId() {
        return id;
    }

    public Long getVin() {
        return vin;
    }

    public void setVin(Long vin) { this.vin = vin; }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
    }
}
