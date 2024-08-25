package com.example.webgostar.controller;

import com.example.webgostar.entity.PersonReq;
import com.example.webgostar.entity.PersonRes;
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

    @GetMapping(value = "/getAll" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonRes>> getAllPersons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        List<PersonRes> list = service.getAllPersons(page, size, sortBy, sortDir);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonRes> getPerson(@PathVariable("id") Long personId) {
        PersonRes person = service.getPerson(personId);
        return ResponseEntity.ok(person);
    }

    @PostMapping(value = "/save" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> savePerson(@RequestBody PersonReq personReq) {
        service.savePerson(personReq);
        return ResponseEntity.ok("Person Created Successfully");
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updatePerson(@RequestBody PersonReq personReq) {
        service.updatePerson(personReq);
        return ResponseEntity.ok("Person Updated Successfully");
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deletePerson(@PathVariable("id") Long personId) {
        service.deletePerson(personId);
        return ResponseEntity.ok("Person Deleted Successfully");
    }
}
