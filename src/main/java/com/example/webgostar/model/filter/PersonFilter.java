package com.example.webgostar.model.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonFilter {

    private String firstName;

    private String lastName;

    private Long nationalCode;
}
