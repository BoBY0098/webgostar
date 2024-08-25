package com.example.webgostar.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonFilter {

    private String firstName;

    private String lastName;

    private Long nationalCode;
}
