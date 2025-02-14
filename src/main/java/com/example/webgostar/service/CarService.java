package com.example.webgostar.service;

import com.example.webgostar.exception.CustomServiceException;
import com.example.webgostar.model.dto.CarRequest;
import com.example.webgostar.model.dto.CarResponse;
import com.example.webgostar.model.entity.CarEntity;
import com.example.webgostar.model.filter.CarFilter;
import com.example.webgostar.model.entity.PersonEntity;
import com.example.webgostar.repository.CarRepository;
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
public class CarService {

    private final CarRepository repository;
    private final PersonService personService;

    public void saveCar(CarRequest carRequest) {
        validation(carRequest);
        try {
            CarEntity newCar = new CarEntity(carRequest);
            repository.save(newCar);
        } catch (Exception e) {
            throw new CustomServiceException("Invalid ownerId: " + carRequest.getOwnerId());
        }
    }

    public List<CarResponse> getAllCars(int page, int size, String sortBy, String sortDir, CarFilter carFilter){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<CarEntity> spec = Specification.where(null);
        if (carFilter.getName() != null && !carFilter.getName().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + carFilter.getName() + "%"));
        }
        if (carFilter.getPlateNumber() != null && !carFilter.getPlateNumber().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("plateNumber"), "%" + carFilter.getPlateNumber() + "%"));
        }
        Page<CarEntity> carList = repository.findAll(spec, pageable);
        List<CarResponse> responseList = new ArrayList<>();
        for (CarEntity car : carList) {
            responseList.add(new CarResponse(car.getId(), car.getName(), car.getPlateNumber(), car.getOwner().getId()));
        }
        return responseList;
    }

    public CarResponse getCar(Long carId) {
        CarEntity car = findByCarId(carId);
        return new CarResponse(car.getId(), car.getName(), car.getPlateNumber(), car.getOwner().getId());
    }

    public CarResponse updateCar(CarRequest carRequest) {
        CarEntity car = findByCarId(carRequest.getCarId());
        validation(carRequest);
        PersonEntity newOwner = personService.findPersonById(carRequest.getOwnerId());
        car.setName(carRequest.getName());
        car.setPlateNumber(carRequest.getPlateNumber());
        car.setOwner(newOwner);
        repository.save(car);
        return new CarResponse(car.getId(), car.getName(), car.getPlateNumber(), car.getOwner().getId());
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

    public void validation(CarRequest carRequest) {
        if (carRequest.getOwnerId() == null) {
            throw new CustomServiceException("Could NOT create a Car without OwnerID");
        }
        if (carRequest.getPlateNumber() == null) {
            throw new CustomServiceException("Plate Number is Null");
        }
        if (carRequest.getName() == null) {
            throw new CustomServiceException("Car Name is Null");
        }
        isPlateNumberExist(carRequest.getPlateNumber());
        isPlateNumber8Digits(carRequest.getPlateNumber());
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
