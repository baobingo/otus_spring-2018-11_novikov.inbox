package ru.otus.spring11.endpoint;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
public class SimpleEndpoint {

    private SimpleEndpointHandler simpleEndpointHandler;

    public SimpleEndpoint(SimpleEndpointHandler simpleEndpointHandler) {
        this.simpleEndpointHandler = simpleEndpointHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> composedRoutes() {
        return route()
            .GET("/api/books", simpleEndpointHandler::getAllBooks)
            .GET("/api/books/{id}/reviews", simpleEndpointHandler::bookReviews)
            .GET("/api/books/{id}", simpleEndpointHandler::getBook)
            .PUT("/api/books", accept(APPLICATION_JSON), simpleEndpointHandler::updateBook)
            .POST("/api/books", accept(APPLICATION_JSON), simpleEndpointHandler::insertBook)
            .DELETE("/api/books/{id}", simpleEndpointHandler::deleteBook)
            .build();
    }
}
