package ru.otus.spring08.bee.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.spring08.domain.Author;
import ru.otus.spring08.domain.Book;
import ru.otus.spring08.domain.Genre;
import ru.otus.spring08.domain.Sequences;

import java.util.stream.IntStream;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "startupFill", author = "baobingo")
    public void startupFill(MongoTemplate mongoTemplate) {
        IntStream.range(0, 10).forEach(i ->{
            Book book = new Book("Book #" + i, new Author("Author #" + i), new Genre("Genre #" + i));
            Sequences counter = mongoTemplate.findAndModify(query(where("_id").is("customSequences")), new Update().inc("seq",1), options().returnNew(true).upsert(true), Sequences.class);
            book.setId(counter.getSeq());
            mongoTemplate.insert(book);
        });
    }
}
