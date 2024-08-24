package com.example.webgostar.service;

import com.example.webgostar.entity.PersonReq;
import com.example.webgostar.entity.PersonRes;
import com.example.webgostar.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repository;

    public void savePerson(PersonReq personReq) {

    }

    public PersonRes getPerson(Long personId) {
        return null;
    }

    public PersonRes updatePerson(Long personId , PersonReq personReq) {
        return null;
    }

    public void deletePerson(Long personId) {

    }
}
