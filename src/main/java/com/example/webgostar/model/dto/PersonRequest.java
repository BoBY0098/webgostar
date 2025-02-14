package com.example.webgostar.model.dto;

import lombok.Data;

@Data
public class PersonRequest {

    private Long personId;

    private String firstName;

    private String lastName;

    private Long nationalCode;
}
