package ru.otus.spring17.endpoint.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import ru.otus.spring17.repository.BookRepositoryReactive;

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

        result.put("Total reviews count", String.valueOf(getReviewCount()));
        result.put("Total books", String.valueOf(getBookCount()));
        result.put("Total count of book w/out reviews", String.valueOf(getBookCountWoutReviews()));
        result.put("ID of most commented book", String.valueOf(getMostCommentedBook()));

        return result;
    }

    private Integer getReviewCount(){
        return bookRepositoryReactive.findAll().map(book -> book.getReviews().size()).defaultIfEmpty(0).reduce((integer, integer2) -> integer + integer2).block();
    }

    private Long getBookCount(){
        return bookRepositoryReactive.count().defaultIfEmpty(0L).block();
    }

    private Long getBookCountWoutReviews(){
        return bookRepositoryReactive.findAll().filter(book -> book.getReviews().size() == 0).count().defaultIfEmpty(0L).block();
    }

    private int getMostCommentedBook(){
        return bookRepositoryReactive.findAll().sort((book1, book2)->
                book1.getReviews().size() > book2.getReviews().size() ? book1.getReviews().size() : book2.getReviews().size()
        ).take(1).blockFirst().getId();
    }

}
