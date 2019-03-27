package ru.otus.spring15.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "penalty")
public class Penalty {
    private String id;
    @DBRef
    private Vehicle vehicle;
    private BigDecimal cost;
    private boolean paid;

    public Penalty(Vehicle vehicle, BigDecimal cost) {
        this.vehicle = vehicle;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid() {
        this.paid = true;
    }
}
