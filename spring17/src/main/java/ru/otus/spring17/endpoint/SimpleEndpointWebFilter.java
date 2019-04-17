package ru.otus.spring17.endpoint;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class SimpleEndpointWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (exchange.getRequest().getURI().getPath().equals("/")) {
            return chain.filter(exchange.mutate().request(exchange.getRequest().mutate().path("/index.html").build()).build());
        }
        if (exchange.getRequest().getURI().getPath().contains("/reviews/")) {
            return chain.filter(exchange.mutate().request(exchange.getRequest().mutate().path("/index.html").build()).build());
        }
        if (exchange.getRequest().getURI().getPath().contains("/edit/")) {
            return chain.filter(exchange.mutate().request(exchange.getRequest().mutate().path("/index.html").build()).build());
        }
        if (exchange.getRequest().getURI().getPath().contains("/add/")) {
            return chain.filter(exchange.mutate().request(exchange.getRequest().mutate().path("/index.html").build()).build());
        }

        return chain.filter(exchange);
    }
}
