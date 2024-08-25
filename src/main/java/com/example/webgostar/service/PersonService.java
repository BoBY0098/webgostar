package com.example.webgostar.service;

import com.example.webgostar.entity.*;
import com.example.webgostar.exception.CustomServiceException;
import com.example.webgostar.repository.CarRepository;
import com.example.webgostar.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repository;
    private final CarRepository carRepository;

    public void savePerson(PersonReq personReq) {
        validation(personReq);
        PersonEntity newPerson = new PersonEntity(personReq);
        repository.save(newPerson);
    }

    public List<PersonRes> getAllPersons(int page, int size, String sortBy, String sortDir, PersonFilter personFilter) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<PersonEntity> spec = Specification.where(null);
        if (personFilter.getFirstName() != null && !personFilter.getFirstName().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("firstName"), "%" + personFilter.getFirstName() + "%"));
        }

        if (personFilter.getLastName() != null && !personFilter.getLastName().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("lastName"), "%" + personFilter.getLastName() + "%"));
        }

        if (personFilter.getNationalCode() != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("nationalCode"), personFilter.getNationalCode()));
        }
        Page<PersonEntity> personList = repository.findAll(spec, pageable);
        List<PersonRes> responseList = new ArrayList<>();
        for (PersonEntity person : personList) {
            responseList.add(new PersonRes(person.getId(), person.getFirstName(), person.getLastName(), person.getNationalCode()));
        }
        return responseList;
    }

    public PersonRes getPerson(Long personId) {
        PersonEntity person = findPersonById(personId);
        return new PersonRes(person.getId() , person.getFirstName() , person.getLastName() , person.getNationalCode());
    }

    public PersonRes updatePerson(PersonReq personReq) {
        validation(personReq);
        PersonEntity person = findPersonById(personReq.getPersonId());
        person.setFirstName(personReq.getFirstName());
        person.setLastName(personReq.getLastName());
        person.setNationalCode(personReq.getNationalCode());
        repository.save(person);
        return new PersonRes(person.getId() , person.getFirstName() , person.getLastName() , person.getNationalCode());
    }

    public void deletePerson(Long personId) {
        PersonEntity person = findPersonById(personId);
        isPersonOwner(personId);
        repository.delete(person);
    }

    public PersonEntity findPersonById(Long personId) {
        Optional<PersonEntity> person = repository.findByPersonId(personId);
        if (person.isEmpty()) {
            throw new CustomServiceException("Person Not Found with this ID : " + personId);
        }
        return person.get();
    }

    public void isPersonOwner(Long personId) {
        Optional<CarEntity> personCar = carRepository.findByPersonId(personId);
        if (personCar.isPresent()){
            throw new CustomServiceException("Couldn't Delete Person who is Owner of Car with ID : " + personCar.get().getId());
        }
    }

    public void validation(PersonReq personReq) {
        if (personReq.getNationalCode() == null) {
            throw new CustomServiceException("Person National Code Is Null");
        }
        if (personReq.getFirstName() == null) {
            throw new CustomServiceException("First Name Is Null");
        }
        if (personReq.getLastName() == null) {
            throw new CustomServiceException("Last Name Is Null");
        }
        nationalCodeValidation(personReq.getNationalCode());
    }

    public void nationalCodeValidation(Long nationalCode) {
        boolean isCodePresent = isNationalCodeExist(nationalCode);
        if (isCodePresent) {
            throw new CustomServiceException("National Code already exist");
        }
        String nationalCodeStr = nationalCode.toString();
        if (nationalCodeStr.length() > 10) {
            throw new CustomServiceException("National Code is More than 10 Digits");
        }
        if(nationalCodeStr.length() < 10) {
            throw new CustomServiceException("National Code is Less than 10 Digits");
        }
    }

    public Boolean isNationalCodeExist(Long nationalCode) {
        Optional<Long> code = repository.findAllByNationalCode(nationalCode);
        return code.isPresent();
    }
}
