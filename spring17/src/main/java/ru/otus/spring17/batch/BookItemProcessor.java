package ru.otus.spring17.batch;

import org.springframework.batch.item.ItemProcessor;
import ru.otus.spring17.batch.domain.AuthorSQL;
import ru.otus.spring17.batch.domain.BookSQL;
import ru.otus.spring17.batch.domain.GenreSQL;
import ru.otus.spring17.batch.domain.ReviewSQL;
import ru.otus.spring17.domain.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BookItemProcessor implements ItemProcessor<Book, BookSQL> {
    @Override
    public BookSQL process(Book book) throws Exception {
        BookSQL bookSQL = new BookSQL();
        bookSQL.setName(book.getName());
        bookSQL.setAuthor(new AuthorSQL(book.getAuthor().getName()));
        bookSQL.setGenre(new GenreSQL(book.getGenre().getName()));
        List<ReviewSQL> reviewSQLS = book.getReviews().stream().map(review -> {
            return new ReviewSQL(review.getAuthor(), review.getBody());
        }).collect(Collectors.toList());
        bookSQL.setReviews(reviewSQLS);
        return bookSQL;
    }
}
