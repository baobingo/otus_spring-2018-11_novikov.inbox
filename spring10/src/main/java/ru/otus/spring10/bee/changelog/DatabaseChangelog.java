package ru.otus.spring10.bee.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring10.domain.Author;
import ru.otus.spring10.domain.Book;
import ru.otus.spring10.domain.Genre;
import ru.otus.spring10.domain.Review;
import ru.otus.spring10.service.SequenceService;
import ru.otus.spring10.service.SimpleSequenceService;

import java.util.stream.IntStream;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "startupFill", author = "baobingo")
    public void startupFill(MongoTemplate mongoTemplate, Environment environment) {
        IntStream.range(0, 10).forEach(i ->{
            Book book = new Book("Book #" + i, new Author("Author #" + i), new Genre("Genre #" + i));
            SequenceService sequenceService = new SimpleSequenceService(environment.getProperty("spring.data.mongodb.customseq"), mongoTemplate);
            book.setId(sequenceService.getNextSequence());
            IntStream.range(0, 10).forEach(x ->{book.addReview(new Review(sequenceService.getNextSequence(),"Author #" + x, "Review body"));});
            mongoTemplate.insert(book);
        });
    }
}
