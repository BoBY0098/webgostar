package com.example.webgostar.model.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarFilter {

    private String name;
    private String plateNumber;
    private int page = 0;
    private int size = 10;
    private String sortBy = "name";
    private String sortDir = "asc";

    public CarFilter(String name, String plateNumber) {
        this.name = name;
        this.plateNumber = plateNumber;
    }
}
