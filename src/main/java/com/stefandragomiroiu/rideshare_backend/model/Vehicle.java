package com.stefandragomiroiu.rideshare_backend.model;

import org.springframework.data.annotation.Id;

public class Vehicle {
    @Id
    private String plateNumber;
    private String brand;
    private String model;
    private String color;
    private Integer registrationYear;
    private Integer seats;
    private Long owner;
    private String image;

    public Vehicle(String plateNumber, String brand, String model, String color, Integer registrationYear, Integer seats, Long owner, String image) {
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.registrationYear = registrationYear;
        this.seats = seats;
        this.owner = owner;
        this.image = image;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getRegistrationYear() {
        return registrationYear;
    }

    public void setRegistrationYear(Integer registrationYear) {
        this.registrationYear = registrationYear;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
