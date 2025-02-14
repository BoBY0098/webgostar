package com.example.webgostar.model.dto;

import lombok.Data;

@Data
public class CarResponse {

    private Long id;

    private String name;

    private String plateNumber;

    private Long ownerId;

    public CarResponse(Long carId, String name, String plateNumber, Long ownerId) {
        this.id = carId;
        this.name = name;
        this.plateNumber = plateNumber;
        this.ownerId = ownerId;
    }
}
