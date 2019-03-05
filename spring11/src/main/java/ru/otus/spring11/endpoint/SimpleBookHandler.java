package ru.otus.spring11.endpoint;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.spring11.domain.Book;
import ru.otus.spring11.repository.BookRepositoryReactive;
import ru.otus.spring11.service.SequenceService;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class SimpleBookHandler {

    private BookRepositoryReactive repository;
    private SequenceService sequenceService;

    public SimpleBookHandler(BookRepositoryReactive repository, SequenceService sequenceService) {
        this.repository = repository;
        this.sequenceService = sequenceService;
    }

    Mono<ServerResponse> getAllBooks(ServerRequest request){
        return ok().contentType(APPLICATION_JSON).body(repository.findAll(), Book.class);
    }

    Mono<ServerResponse> bookReviews(ServerRequest request){
        return repository.findById(Long.parseLong(request.pathVariable("id"))).flatMap(book -> ok().contentType(APPLICATION_JSON).body(fromObject(book.getReviews())));
    }

    Mono<ServerResponse> getBook(ServerRequest request){
        return repository.findById(Long.parseLong(request.pathVariable("id"))).flatMap(book -> ok().contentType(APPLICATION_JSON).body(fromObject(book)));
    }

    Mono<ServerResponse> updateBook(ServerRequest request) {
        return request.bodyToMono(Book.class).doOnNext(requestBook -> repository.findById((long)requestBook.getId()).doOnNext(book ->
        {
            book.setName(requestBook.getName());
            book.getAuthor().setName(requestBook.getAuthor().getName());
            book.getGenre().setName(requestBook.getGenre().getName());
            repository.save(book).subscribe();
        }).subscribe()).then(ok().build());
    }

    Mono<ServerResponse> insertBook(ServerRequest request){
        return request.bodyToMono(Book.class).doOnNext(book ->
        {
            book.setId(sequenceService.getNextSequence());
            repository.insert(book).subscribe();
        }).then(ok().build());
    }

    Mono<ServerResponse> deleteBook(ServerRequest request){
        return repository.findById(Long.parseLong(request.pathVariable("id")))
                .doOnNext(book -> repository.delete(book).subscribe()).then(ok().build());
    }

}
