package ru.otus.spring14.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.devtools.restart.Restarter;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "restart")
public class SimpleActuatorRestart {
    @WriteOperation
    public void restart() {
        Restarter.getInstance().restart();
    }
}
