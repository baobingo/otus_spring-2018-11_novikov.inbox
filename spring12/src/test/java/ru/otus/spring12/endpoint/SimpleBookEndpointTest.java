package ru.otus.spring12.endpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Mono;
import ru.otus.spring12.service.SequenceService;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_CLASS)
class SimpleBookEndpointTest {

    @Autowired
    private RouterFunction routerFunction;
    @Autowired
    private SequenceService sequenceService;
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
                .expectBody()
                .jsonPath("$.[0].name").isEqualTo("Book #0").jsonPath("$.[0].author.name").isEqualTo("Author #0").jsonPath("$.[0].genre.name").isEqualTo("Genre #0")
                .jsonPath("$.[1].name").isEqualTo("Book #1").jsonPath("$.[1].author.name").isEqualTo("Author #1").jsonPath("$.[1].genre.name").isEqualTo("Genre #1");
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
                .uri("/api/books/23")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.name").isEqualTo("Book #2");
    }

    @Test
    void updateBook() throws InterruptedException{
        client.put()
                .uri("/api/books")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .body(Mono.just("{\"id\":\"1\", \"name\":\"Book #11\", \"author\":\"Author #11\", \"genre\":\"Genre #11\"}"), String.class)
                .exchange()
                .expectStatus()
                .isOk();
        Thread.sleep(500);
        client.get()
                .uri("/api/books/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody().jsonPath("$.name").isEqualTo("Book #11").jsonPath("$.author.name").isEqualTo("Author #11").jsonPath("$.genre.name").isEqualTo("Genre #11");
    }

    @Test
    void addBook() throws InterruptedException{
        int id = sequenceService.getNextSequence();
        client.post()
                .uri("/api/books")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .body(Mono.just("{\"id\":\"" + id + "\", \"name\":\"Book #999\", \"author\":\"Author #999\", \"genre\":\"Genre #999\"}"), String.class)
                .exchange()
                .expectStatus()
                .isOk().expectBody();
        Thread.sleep(500);
        id++;
        client.get()
                .uri("/api/books/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody().jsonPath("$.name").isEqualTo("Book #999").jsonPath("$.author.name").isEqualTo("Author #999").jsonPath("$.genre.name").isEqualTo("Genre #999");
    }

    @Test
    void deleteBook(){
        client.get()
                .uri("/api/books/12")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.name").isNotEmpty();
        client.delete()
                .uri("/api/books/12")
                .exchange()
                .expectStatus()
                .isOk();
        client.get()
                .uri("/api/books/12")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody().isEmpty();
    }
}