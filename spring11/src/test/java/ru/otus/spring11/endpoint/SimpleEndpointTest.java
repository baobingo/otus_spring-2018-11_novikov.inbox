package ru.otus.spring11.endpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
class SimpleEndpointTest {

    @Autowired
    private RouterFunction routerFunction;
    private WebTestClient client;

    @BeforeEach
    void SimpleEndpointTest() {
        this.client = WebTestClient
                .bindToRouterFunction(routerFunction)
                .build();
    }

    @Test
    void getAllBooks() {
        client.get()
                .uri("/api/books")
                .exchange()
                .expectStatus()
                .isOk().expectHeader().contentType(APPLICATION_JSON)
                .expectBody().jsonPath("$").isArray();
    }

    @Test
    void bookReviews(){
        client.get()
                .uri("/api/books/1/reviews")
                .exchange()
                .expectStatus()
                .isOk().expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.[0].reviewId").isEqualTo("2");
        ;
    }

    @Test
    void getBook(){
        client.get()
                .uri("/api/books/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.name").isEqualTo("Book #0");
    }

    @Test
    void updateBook(){
        client.put()
                .uri("/api/books")
                .contentType(APPLICATION_JSON)
                .body(Mono.just("{\"id\":\"1\", \"name\":\"Book #0\", \"author\":\"Author #0\", \"genre\":\"Genre #0\"}"), String.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void addBook(){
        client.post()
                .uri("/api/books")
                .contentType(APPLICATION_JSON)
                .body(Mono.just("{\"id\":\"999\", \"name\":\"Book #0\", \"author\":\"Author #0\", \"genre\":\"Genre #0\"}"), String.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void deleteBook(){
        client.delete()
                .uri("/api/books/1")
                .exchange()
                .expectStatus()
                .isOk();
    }
}