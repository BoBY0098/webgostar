package com.example.webgostar.entity;

import lombok.Data;

@Data
public class PersonRes {

    private Long id;

    private String firstName;

    private String lastName;

    private Long nationalCode;

    public PersonRes (Long personId , String firstName , String lastName , Long nationalCode) {
        this.id = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
    }
}
