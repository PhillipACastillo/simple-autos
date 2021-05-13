package com.ascent.autosapi;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "automobiles")
public class Automobile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String make;
    private String color;
    private Long vin = new Random().nextLong();
    public Automobile() {
    }

//    public Automobile(int id) {
//        this.id = id;
//        this.color = "Color " + id;
//        this.make = "Make " + id;
//        this.vin = new Random().nextLong();
//    }

    public Automobile(String make, String color) {
        this.id = new Random().nextInt();
        this.make = make;
        this.color = color;
//        this.vin =  1L + (long) (Math.random() * (1000L - 1L));
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

    public Long getVin() { return vin; }

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
