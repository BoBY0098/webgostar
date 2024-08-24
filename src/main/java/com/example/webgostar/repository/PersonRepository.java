package com.example.webgostar.repository;

import com.example.webgostar.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    @Query("SELECT pe.nationalCode FROM PersonEntity pe WHERE pe.nationalCode = :nationalCode")
    public Optional<Long> findAllByNationalCode(@Param("nationalCode") Long nationalCode);

    @Query("SELECT pe FROM PersonEntity pe WHERE pe.id = :personId")
    public Optional<PersonEntity> findByPersonId(@Param("personId") Long personId);
}
