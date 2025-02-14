package com.example.webgostar.model.entity;

import com.example.webgostar.model.dto.PersonRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "persons")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "national_code" , length = 10 , unique = true , nullable = false)
    private Long nationalCode;

    public PersonEntity (PersonRequest personRequest) {
        setId(id);
        this.firstName = personRequest.getFirstName();
        this.lastName = personRequest.getLastName();
        this.nationalCode = personRequest.getNationalCode();
    }

    public PersonEntity(Long ownerId) {
        setId(ownerId);
    }
}
