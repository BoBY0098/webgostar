package com.example.webgostar.repository;

import com.example.webgostar.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> , JpaSpecificationExecutor<CarEntity> {

    @Query("SELECT ce FROM CarEntity ce WHERE ce.owner.id = :ownerId")
    public Optional<CarEntity> findByPersonId(@Param("ownerId") Long ownerId);

    @Query("SELECT ce FROM CarEntity ce WHERE ce.plateNumber = :plateNumber")
    public Optional<CarEntity> findByPlateNumber(@Param("plateNumber") String plateNumber);

    @Query("SELECT ce FROM CarEntity ce WHERE ce.id = :carId")
    public Optional<CarEntity> findByCarId(@Param("carId") Long carId);
}
