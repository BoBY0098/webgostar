package com.example.webgostar.model.dto;

import lombok.Data;

@Data
public class PersonResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private Long nationalCode;

    public PersonResponse(Long personId , String firstName , String lastName , Long nationalCode) {
        this.id = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
    }
}
