package ru.otus.spring15.endpoint;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring15.domain.Penalty;
import ru.otus.spring15.domain.Vehicle;

import java.util.Optional;

@MessagingGateway
public interface Radar{
    @Gateway(requestChannel = "aimChannel")
    void aim(Vehicle vehicle);
    @Gateway(replyChannel = "resultChannel")
    Optional<Penalty> result();
}
