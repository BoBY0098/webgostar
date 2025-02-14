package com.example.webgostar.controller;

import com.example.webgostar.model.filter.CarFilter;
import com.example.webgostar.model.dto.CarRequest;
import com.example.webgostar.model.dto.CarResponse;
import com.example.webgostar.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService service;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CarResponse>> getAllCars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String plateNumber
            ){
        CarFilter carFilter = new CarFilter(name, plateNumber);
        List<CarResponse> list = service.getAllCars(page, size, sortBy, sortDir, carFilter);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarResponse> getCar(@PathVariable("id") Long carId) {
        CarResponse car = service.getCar(carId);
        return ResponseEntity.ok(car);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveCar(@RequestBody CarRequest carRequest) {
        service.saveCar(carRequest);
        return ResponseEntity.ok("Car Created Successfully");
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCar(@RequestBody CarRequest carRequest) {
        service.updateCar(carRequest);
        return ResponseEntity.ok("Car Updated Successfully");
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCar(@PathVariable("id") Long carId) {
        service.deleteCar(carId);
        return ResponseEntity.ok("Car Deleted Successfully");
    }
}
