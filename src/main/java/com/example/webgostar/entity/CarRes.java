package com.example.webgostar.entity;

import lombok.Data;

@Data
public class CarRes {

    private Long id;

    private String name;

    private String plateNumber;

    private Long ownerId;

    public CarRes(Long carId, String name, String plateNumber, Long ownerId) {
        this.id = carId;
        this.name = name;
        this.plateNumber = plateNumber;
        this.ownerId = ownerId;
    }
}
