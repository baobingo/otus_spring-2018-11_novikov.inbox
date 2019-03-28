package ru.otus.spring15.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.spring15.domain.Penalty;
import ru.otus.spring15.domain.Vehicle;
import ru.otus.spring15.repository.PenaltyRepository;
import ru.otus.spring15.repository.VehicleRepository;

@Service
public class TrafficJudgeService {

    private static Logger logger = LoggerFactory.getLogger(TrafficJudgeService.class);
    private VehicleRepository vehicleRepository;
    private PenaltyRepository penaltyRepository;

    public TrafficJudgeService(VehicleRepository vehicleRepository, PenaltyRepository penaltyRepository) {
        this.vehicleRepository = vehicleRepository;
        this.penaltyRepository = penaltyRepository;
    }

    public Vehicle logVehicle(Vehicle vehicle){
        logger.info("Store new Vehicle, speed: {} speed limit: {}", vehicle.getSpeed(), vehicle.getSpeedLimit());
        return vehicle;
    }

    public Vehicle updateChecked(Vehicle vehicle){
        vehicle.setChecked();
        return vehicleRepository.save(vehicle);
    }

    public Penalty logPenalty(Penalty penalty){
        logger.info("Store new FAULT, car: {} penalty cost: {}", penalty.getVehicle().getId(), penalty.getCost());
        return penalty;
    }

    public Penalty logPaid(Penalty penalty){
        logger.info("Penalty was SENT to OWNER, car: {} penalty cost: {}", penalty.getVehicle().getId(), penalty.getCost());
        penalty.setPaid();
        return penaltyRepository.save(penalty);
    }
}
