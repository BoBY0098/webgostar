package com.example.webgostar.model.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonFilter {

    private String firstName;
    private String lastName;
    private Long nationalCode;
    private int page = 0;
    private int size = 10;
    private String sortBy = "name";
    private String sortDir = "asc";

    public PersonFilter (String firstName, String lastName, Long nationalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
    }
}
