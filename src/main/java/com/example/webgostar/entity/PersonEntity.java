package com.example.webgostar.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "persons")
@Data
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "national_code" , length = 10 , unique = true)
    private Long nationalCode;
}
