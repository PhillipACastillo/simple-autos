package com.ascent.autosapi;

public class Automobile {
    private int id;
    private String make;
    private String color;

    public Automobile() {
    }

    public Automobile(int id) {
        this.id = id;
        this.color = "Color " + id;
        this.make = "Make " + id;
    }

    public Automobile(int id, String make, String color) {
        this.id = id;
        this.make = make;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Automobile{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

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
