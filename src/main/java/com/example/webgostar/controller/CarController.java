package com.example.webgostar.controller;

import com.example.webgostar.entity.CarReq;
import com.example.webgostar.entity.CarRes;
import com.example.webgostar.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService service;

    @GetMapping(value = "/getAll" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CarRes>> getAllCars(){
        List<CarRes> list = service.getAllCars();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarRes> getCar(@PathVariable("id") Long carId) {
        CarRes car = service.getCar(carId);
        return ResponseEntity.ok(car);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveCar(@RequestBody CarReq carReq) {
        service.saveCar(carReq);
        return ResponseEntity.ok("Car Created Successfully");
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCar(@RequestBody CarReq carReq) {
        service.updateCar(carReq);
        return ResponseEntity.ok("Car Updated Successfully");
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCar(@PathVariable("id") Long carId) {
        service.deleteCar(carId);
        return ResponseEntity.ok("Car Deleted Successfully");
    }
}
