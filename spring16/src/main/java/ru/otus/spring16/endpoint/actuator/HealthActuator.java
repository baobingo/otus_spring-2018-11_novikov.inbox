package ru.otus.spring16.endpoint.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class HealthActuator implements ReactiveHealthIndicator{

    private ApplicationContext appContext;

    public HealthActuator(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public Mono<Health> health() {
        if(appContext.containsBean("bookRepositoryReactive")) {
            return Mono.justOrEmpty(Health.up().build());
        }else {
            return Mono.justOrEmpty(Health.down().build());
        }
    }
}
