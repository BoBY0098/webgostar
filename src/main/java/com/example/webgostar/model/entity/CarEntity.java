package com.example.webgostar.model.entity;

import com.example.webgostar.model.dto.CarRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cars")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "plate_number" , length = 8 , unique = true , nullable = false)
    private String plateNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id" , nullable = false)
    @JsonIgnore
    private PersonEntity owner;

    @Column(name = "owner_id" , insertable = false, updatable = false)
    private Long ownerId;

    public CarEntity(CarRequest carRequest) {
        setId(id);
        this.name = carRequest.getName();
        this.plateNumber = carRequest.getPlateNumber();
        this.owner = new PersonEntity(carRequest.getOwnerId());
    }
}
