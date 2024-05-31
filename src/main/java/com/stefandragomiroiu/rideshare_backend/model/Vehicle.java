package com.stefandragomiroiu.rideshare_backend.model;

public record Vehicle(
        String plateNumber,
        String brand,
        String model,
        String color,
        Integer registrationYear,
        Integer seats,
        Long owner,
        String image
) {
}
