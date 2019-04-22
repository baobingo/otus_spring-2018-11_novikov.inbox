package ru.otus.spring18.endpoint;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring18.domain.Book;
import ru.otus.spring18.domain.dto.ObjectResource;
import ru.otus.spring18.repository.BookRepositoryReactive;
import ru.otus.spring18.service.SequenceService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequestMapping("/api/books")
public class SimpleBookHandler {

    private BookRepositoryReactive repository;
    private SequenceService sequenceService;

    public SimpleBookHandler(BookRepositoryReactive repository, SequenceService sequenceService) {
        this.repository = repository;
        this.sequenceService = sequenceService;
    }

    Mono<ServerResponse> getAllBooksHateaos(ServerRequest request){
        Flux<ObjectResource> bookFlux = repository.findAll().map(book -> {
            ObjectResource objectResource = new ObjectResource(book);
            objectResource.add(linkTo(SimpleBookHandler.class).slash(book.getId()).withSelfRel().withType("GET").withTitle("Get certain book detail"));
            objectResource.add(linkTo(SimpleBookHandler.class).slash(book.getId()).withSelfRel().withType("DELETE").withTitle("Delete certain book"));
            objectResource.add(linkTo(SimpleBookHandler.class).slash(book.getId()).slash("reviews").withSelfRel().withType("GET").withTitle("Get certain book reviews"));
            return objectResource;
        });
        return ok().contentType(APPLICATION_JSON).body(bookFlux, ObjectResource.class);
    }

    Mono<ServerResponse> bookReviews(ServerRequest request){
        return repository.findById(Long.parseLong(request.pathVariable("id"))).flatMap(book -> ok().contentType(APPLICATION_JSON).body(fromObject(book.getReviews())));
    }

    Mono<ServerResponse> getBook(ServerRequest request){
        return repository.findById(Long.parseLong(request.pathVariable("id"))).flatMap(book -> ok().contentType(APPLICATION_JSON).body(fromObject(book)));
    }

    Mono<ServerResponse> updateBook(ServerRequest request) {
        return request.bodyToMono(Book.class)
                .flatMap(book -> repository.findById((long)book.getId())
                        .flatMap(book1 -> {
                            book1.setName(book.getName());
                            book1.getAuthor().setName(book.getAuthor().getName());
                            book1.getGenre().setName(book.getGenre().getName());
                            return repository.save(book1);
                        })
                ).then(ok().build());
    }

    Mono<ServerResponse> insertBook(ServerRequest request){
        return request.bodyToMono(Book.class).flatMap(book -> {
            book.setId(sequenceService.getNextSequence());
            return repository.insert(book).then(ok().build());
        });
    }

    Mono<ServerResponse> deleteBook(ServerRequest request){
        return repository.findById(Long.parseLong(request.pathVariable("id"))).flatMap(
                book -> repository.delete(book)).then(ok().build());
    }

}
