package com.example.webgostar.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cars")
@Data
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "plate" , length = 8)
    private String plate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private PersonEntity person;
}
