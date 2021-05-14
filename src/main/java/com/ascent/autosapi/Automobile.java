package com.ascent.autosapi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Entity
@Table(name = "automobiles")
@JsonInclude(JsonInclude.Include.NON_NULL)
// @JsonPropertyOrder({"searchParameters","size","autos"})
public class Automobile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String make;
    private String color;
    private String owner;
    private int year; //Initialized to zero, if not specified
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date purchaseDate;
    @Column(unique = true)
    private Long vin = Math.abs(new Random().nextLong());
    public Automobile() {
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
//    public Automobile(int id) {
//        this.id = id;
//        this.color = "Color " + id;
//        this.make = "Make " + id;
//        this.vin = new Random().nextLong();
//    }

    public Automobile(String color, String make) {
        this.id = new Random().nextInt();
        this.make = make;
        this.color = color;
    }


    @Override
    public String toString() {
        return "Automobile{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", color='" + color + '\'' +
                ", owner='" + owner + '\'' +
                ", year=" + year +
                ", purchaseDate=" + purchaseDate +
                ", vin=" + vin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Automobile)) return false;
        Automobile that = (Automobile) o;
        return id == that.id && year == that.year && make.equals(that.make) && color.equals(that.color) && owner.equals(that.owner) && purchaseDate.equals(that.purchaseDate) && vin.equals(that.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, make, color, owner, year, purchaseDate, vin);
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
