package ru.otus.spring15.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring15.domain.Vehicle;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {
}
