package com.example.webgostar.repository;

import com.example.webgostar.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {

    @Query("SELECT ce FROM CarEntity ce WHERE ce.person.id = :personId")
    public Optional<CarEntity> findByPersonId(@Param("personId") Long personId);
}
