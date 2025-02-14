package com.example.webgostar.controller;

import com.example.webgostar.model.filter.PersonFilter;
import com.example.webgostar.model.dto.PersonRequest;
import com.example.webgostar.model.dto.PersonResponse;
import com.example.webgostar.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService service;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonResponse>> getAllPersons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Long nationalCode
    ) {
        PersonFilter personFilter = new PersonFilter(firstName, lastName, nationalCode);
        List<PersonResponse> list = service.getAllPersons(page, size, sortBy, sortDir, personFilter);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResponse> getPerson(@PathVariable("id") Long personId) {
        PersonResponse person = service.getPerson(personId);
        return ResponseEntity.ok(person);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> savePerson(@RequestBody PersonRequest personRequest) {
        service.savePerson(personRequest);
        return ResponseEntity.ok("Person Created Successfully");
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updatePerson(@RequestBody PersonRequest personRequest) {
        service.updatePerson(personRequest);
        return ResponseEntity.ok("Person Updated Successfully");
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deletePerson(@PathVariable("id") Long personId) {
        service.deletePerson(personId);
        return ResponseEntity.ok("Person Deleted Successfully");
    }
}
