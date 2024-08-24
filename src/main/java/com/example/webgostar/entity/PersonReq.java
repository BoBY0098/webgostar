package com.example.webgostar.entity;

import lombok.Data;

@Data
public class PersonReq {

    private String firstName;

    private String lastName;

    private Long nationalCode;
}
