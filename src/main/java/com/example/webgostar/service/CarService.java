package com.example.webgostar.service;

import com.example.webgostar.entity.CarEntity;
import com.example.webgostar.entity.CarReq;
import com.example.webgostar.entity.CarRes;
import com.example.webgostar.entity.PersonEntity;
import com.example.webgostar.exception.CustomServiceException;
import com.example.webgostar.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository repository;
    private final PersonService personService;

    public void saveCar(CarReq carReq) {
        validation(carReq);
        PersonEntity owner = personService.findPersonById(carReq.getOwnerId());
        CarEntity newCar = new CarEntity(carReq, owner);
        repository.save(newCar);
    }

    public List<CarRes> getAllCars(int page, int size, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CarEntity> carList = repository.findAll(pageable);
        List<CarRes> responseList = new ArrayList<>();
        for (CarEntity car : carList) {
            responseList.add(new CarRes(car.getId(), car.getName(), car.getPlateNumber(), car.getOwner().getId()));
        }
        return responseList;
    }

    public CarRes getCar(Long carId) {
        CarEntity car = findByCarId(carId);
        return new CarRes(car.getId(), car.getName(), car.getPlateNumber(), car.getOwner().getId());
    }

    public CarRes updateCar(CarReq carReq) {
        CarEntity car = findByCarId(carReq.getCarId());
        validation(carReq);
        PersonEntity newOwner = personService.findPersonById(carReq.getOwnerId());
        car.setName(carReq.getName());
        car.setPlateNumber(carReq.getPlateNumber());
        car.setOwner(newOwner);
        repository.save(car);
        return new CarRes(car.getId(), car.getName(), car.getPlateNumber(), car.getOwner().getId());
    }

    public void deleteCar(Long carId) {
        CarEntity car = findByCarId(carId);
        repository.delete(car);
    }

    public CarEntity findByCarId(Long carId) {
        Optional<CarEntity> car = repository.findByCarId(carId);
        if (car.isEmpty()) {
            throw new CustomServiceException("Car Not Found with this ID : " + carId);
        }
        return car.get();
    }

    public void validation(CarReq carReq) {
        if (carReq.getOwnerId() == null) {
            throw new CustomServiceException("Could NOT create a Car without OwnerID");
        }
        if (carReq.getPlateNumber() == null) {
            throw new CustomServiceException("Plate Number is Null");
        }
        if (carReq.getName() == null) {
            throw new CustomServiceException("Car Name is Null");
        }
        isPlateNumberExist(carReq.getPlateNumber());
        isPlateNumber8Digits(carReq.getPlateNumber());
    }

    public void isPlateNumberExist(String plateNumber) {
        Optional<CarEntity> car = repository.findByPlateNumber(plateNumber);
        if (car.isPresent()) {
            throw new CustomServiceException("Car is already exist with this Plate Number and Owner ID is : " + car.get().getOwner().getId());
        }
    }

    public void isPlateNumber8Digits(String plateNumber) {
        if (plateNumber.length() > 8) {
            throw new CustomServiceException("Plate Number is More than 8 Digits");
        }
        if (plateNumber.length() < 8) {
            throw new CustomServiceException("Plate Number is Less than 8 Digits");
        }
    }
}
