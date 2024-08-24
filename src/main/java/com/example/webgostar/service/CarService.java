package com.example.webgostar.service;

import com.example.webgostar.entity.CarReq;
import com.example.webgostar.entity.CarRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarService {

    public void saveCar(CarReq carReq) {

    }

    public CarRes getCar(Long carId) {
        return null;
    }

    public CarRes updateCar(Long carId , CarReq carReq) {
        return null;
    }

    public void deleteCar(Long carId) {

    }
}
