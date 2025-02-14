package com.example.webgostar.service;

import com.example.webgostar.exception.CustomServiceException;
import com.example.webgostar.model.dto.PersonRequest;
import com.example.webgostar.model.dto.PersonResponse;
import com.example.webgostar.model.entity.CarEntity;
import com.example.webgostar.model.entity.PersonEntity;
import com.example.webgostar.model.filter.PersonFilter;
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

    public void savePerson(PersonRequest personRequest) {
        validation(personRequest);
        PersonEntity newPerson = new PersonEntity(personRequest);
        repository.save(newPerson);
    }

    public List<PersonResponse> getAllPersons(PersonFilter personFilter) {
        Sort sort = personFilter.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(personFilter.getSortBy()).ascending() : Sort.by(personFilter.getSortBy()).descending();
        Pageable pageable = PageRequest.of(personFilter.getPage(), personFilter.getSize(), sort);
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
        List<PersonResponse> responseList = new ArrayList<>();
        for (PersonEntity person : personList) {
            responseList.add(new PersonResponse(person.getId(), person.getFirstName(), person.getLastName(), person.getNationalCode()));
        }
        return responseList;
    }

    public PersonResponse getPerson(Long personId) {
        PersonEntity person = findPersonById(personId);
        return new PersonResponse(person.getId() , person.getFirstName() , person.getLastName() , person.getNationalCode());
    }

    public PersonResponse updatePerson(PersonRequest personRequest) {
        validation(personRequest);
        PersonEntity person = findPersonById(personRequest.getPersonId());
        person.setFirstName(personRequest.getFirstName());
        person.setLastName(personRequest.getLastName());
        person.setNationalCode(personRequest.getNationalCode());
        repository.save(person);
        return new PersonResponse(person.getId() , person.getFirstName() , person.getLastName() , person.getNationalCode());
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

    public void validation(PersonRequest personRequest) {
        if (personRequest.getNationalCode() == null) {
            throw new CustomServiceException("Person National Code Is Null");
        }
        if (personRequest.getFirstName() == null) {
            throw new CustomServiceException("First Name Is Null");
        }
        if (personRequest.getLastName() == null) {
            throw new CustomServiceException("Last Name Is Null");
        }
        nationalCodeValidation(personRequest.getNationalCode());
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
