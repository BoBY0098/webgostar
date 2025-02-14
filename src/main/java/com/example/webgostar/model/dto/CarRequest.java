package com.example.webgostar.model.dto;

import lombok.Data;

@Data
public class CarRequest {

    private Long carId;

    private String name;

    private String plateNumber;

    private Long ownerId;
}
