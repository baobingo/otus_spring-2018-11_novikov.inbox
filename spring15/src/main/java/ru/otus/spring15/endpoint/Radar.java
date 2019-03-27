package ru.otus.spring15.endpoint;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring15.domain.Vehicle;

@MessagingGateway
public interface Radar{
    @Gateway(requestChannel = "aimChannel")
    void aim(Vehicle vehicle);
}
