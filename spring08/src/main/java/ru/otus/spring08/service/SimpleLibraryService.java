package ru.otus.spring08.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Service;
import ru.otus.spring08.repo.*;
import ru.otus.spring08.domain.Book;
import ru.otus.spring08.domain.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleLibraryService implements LibraryService {

    private BookRepo bookRepo;
    private SequenceService sequenceService;
    private MongoTemplate mongoTemplate;

    public SimpleLibraryService(BookRepo bookRepo, SequenceService sequenceService, MongoTemplate mongoTemplate) {
        this.bookRepo = bookRepo;
        this.sequenceService = sequenceService;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public long bookCount() {
        return bookRepo.count();
    }

    @Override
    public long authorCount() {
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("author.name"));
        return mongoTemplate.aggregate(aggregation, "books", String.class).getMappedResults().size();
    }

    @Override
    public long genreCount() {
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("genre.name"));
        return mongoTemplate.aggregate(aggregation, "books", String.class).getMappedResults().size();
    }

    @Override
    public List<Book> allBook() {
        return bookRepo.findAll();
    }

    @Override
    public List<Book> authorsBooks(String authorsName) {
        return bookRepo.findBooksByAuthorName(authorsName);
    }

    @Override
    public List<Book> genresBooks(String genresTitle) {
        return bookRepo.findBooksByGenreName(genresTitle);
    }


    @Override
    public void addBook(Book book) {
        book.setId(sequenceService.getNextSequence());
        bookRepo.insert(book);
    }


    @Override
    public void deleteBookById(long id) {
        bookRepo.deleteById(id);
    }


    @Override
    public List<Review> booksReviews(long booksId) {
        List<Review> reviewList = new ArrayList<>();
        Optional<Book> optionalBook = bookRepo.findById(booksId);
        if(optionalBook.isPresent()){
            reviewList = optionalBook.get().getReviews();
        }
        return reviewList;
    }

    @Override
    public void addReview(Review review, long bookId) {
        review.setReviewId(sequenceService.getNextSequence());
        bookRepo.findById(bookId).ifPresent(x->{x.addReview(review); bookRepo.save(x);});
    }

}
