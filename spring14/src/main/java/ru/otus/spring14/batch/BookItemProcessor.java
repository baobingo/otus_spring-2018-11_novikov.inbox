package ru.otus.spring14.batch;

import org.springframework.batch.item.ItemProcessor;
import ru.otus.spring14.batch.domain.AuthorSQL;
import ru.otus.spring14.batch.domain.BookSQL;
import ru.otus.spring14.batch.domain.GenreSQL;
import ru.otus.spring14.batch.domain.ReviewSQL;
import ru.otus.spring14.domain.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BookItemProcessor implements ItemProcessor<Book, BookSQL> {
    @Override
    public BookSQL process(Book book) throws Exception {
        BookSQL bookSQL = new BookSQL(book.getName(), new AuthorSQL(book.getAuthor().getName()), new GenreSQL(book.getGenre().getName()));
        List<ReviewSQL> reviewSQLS = book.getReviews().stream().map(review -> {
            return new ReviewSQL(review.getAuthor(), review.getBody());
        }).collect(Collectors.toList());
        bookSQL.setReviews(reviewSQLS);
        return bookSQL;
    }
}
