package ru.otus.spring16.endpoint;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
@CrossOrigin("*")
public class SimpleBookEndpoint {

    private SimpleBookHandler simpleBookHandler;

    public SimpleBookEndpoint(SimpleBookHandler simpleBookHandler) {
        this.simpleBookHandler = simpleBookHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> composedRoutes() {
        return route()
            .GET("/api/books", simpleBookHandler::getAllBooksHateaos)
            .GET("/api/books/{id}/reviews", simpleBookHandler::bookReviews)
            .GET("/api/books/{id}", simpleBookHandler::getBook)
            .PUT("/api/books", accept(APPLICATION_JSON), simpleBookHandler::updateBook)
            .POST("/api/books", accept(APPLICATION_JSON), simpleBookHandler::insertBook)
            .DELETE("/api/books/{id}", simpleBookHandler::deleteBook)
            .build();
    }
}
