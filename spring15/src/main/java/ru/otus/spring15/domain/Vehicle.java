package ru.otus.spring15.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehicle")
public class Vehicle {
    private String id;
    private int speed;
    private int speedLimit;
    private boolean checked;

    public Vehicle(int speed, int speedLimit) {
        this.speed = speed;
        this.speedLimit = speedLimit;
    }

    public String getId() {
        return id;
    }

    public int getSpeed() {
        return speed;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked() {
        this.checked = true;
    }
}
