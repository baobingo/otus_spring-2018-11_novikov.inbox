package ru.otus.spring16.endpoint.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.otus.spring16.repository.BookRepositoryReactive;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "dashboard")
public class MetricsActuator {

    private BookRepositoryReactive bookRepositoryReactive;
    private Map<String, Object> result;

    public MetricsActuator(BookRepositoryReactive bookRepositoryReactive) {
        this.bookRepositoryReactive = bookRepositoryReactive;
    }

    @ReadOperation
    public Map<String, Object> info(){
        result = new HashMap<>();
        Mono<Integer> reviewCount = bookRepositoryReactive.findAll().map(book -> book.getReviews().size()).reduce((integer, integer2) -> integer + integer2);
        Mono<Long> bookCount = bookRepositoryReactive.count();
        Mono<Long> bookCountWoutReviews = bookRepositoryReactive.findAll().filter(book -> book.getReviews().size() == 0).count();
        int mostCommentedBook = bookRepositoryReactive.findAll().sort((book1, book2)->
                book1.getReviews().size() > book2.getReviews().size() ? book1.getReviews().size() : book2.getReviews().size()
        ).take(1).blockFirst().getId();

        result.put("Total reviews count", String.valueOf(reviewCount.block()));
        result.put("Total books", String.valueOf(bookCount.block()));
        result.put("Total count of book w/out reviews", String.valueOf(bookCountWoutReviews.block()));
        result.put("ID of most commented book", String.valueOf(mostCommentedBook));

        return result;
    }
}
