package com.example.webgostar.entity;

import lombok.Data;

@Data
public class CarReq {

    private Long carId;

    private String name;

    private String plateNumber;

    private Long ownerId;
}
