package ru.otus.spring07.service;

import org.springframework.stereotype.Service;
import ru.otus.spring07.repo.*;
import ru.otus.spring07.domain.Author;
import ru.otus.spring07.domain.Book;
import ru.otus.spring07.domain.Genre;
import ru.otus.spring07.domain.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleLibraryService implements LibraryService {

    private AuthorRepo authorRepo;
    private GenreRepo genreRepo;
    private BookRepo bookRepo;
    private ReviewRepo reviewRepo;

    public SimpleLibraryService(AuthorRepo authorRepo, GenreRepo genreRepo, BookRepo bookRepo, ReviewRepo reviewRepo) {
        this.authorRepo = authorRepo;
        this.genreRepo = genreRepo;
        this.bookRepo = bookRepo;
        this.reviewRepo = reviewRepo;
    }

    @Override
    public long bookCount() {
        return bookRepo.count();
    }

    @Override
    public long authorCount() {
        return authorRepo.count();
    }

    @Override
    public long genreCount() {
        return genreRepo.count();
    }

    @Override
    public List<Book> allBook() {
        return bookRepo.findAll();
    }

    @Override
    public List<Book> authorsBooks(String authorsName) {
        return authorRepo.findByName(authorsName).map(bookRepo::findByAuthor).orElse(new ArrayList<>());
    }

    @Override
    public List<Book> genresBooks(String genresTitle) {
        return genreRepo.findByName(genresTitle).map(bookRepo::findByGenre).orElse(new ArrayList<>());
    }


    @Override
    public void addBook(Book book) {
        authorRepo.findByName(book.getAuthor().getName()).ifPresent(book::setAuthor);
        genreRepo.findByName(book.getGenre().getName()).ifPresent(book::setGenre);
        bookRepo.save(book);
    }

    @Override
    public void addAuthor(Author author) {
        String name = author.getName();
        if(!authorRepo.findByName(name).isPresent()){
            authorRepo.save(author);
        }
    }

    @Override
    public void addGenre(Genre genre) {
        String name = genre.getName();
        if(!genreRepo.findByName(name).isPresent()){
            genreRepo.save(genre);
        }
    }

    @Override
    public void deleteBookById(long id) {
        bookRepo.deleteById(id);
    }

    @Override
    public void deleteAuthorById(long id){
        authorRepo.deleteById(id);
    }

    @Override
    public void deleteGenreById(long id){
        genreRepo.deleteById(id);
    }

    @Override
    public List<Review> booksReviews(long booksId) {
        Optional<Book> book = bookRepo.findById(booksId);
        return book.map(reviewRepo::findByBook).orElse(new ArrayList<>());
    }

    @Override
    public void addReview(Review review, long bookId) {
        bookRepo.findById(bookId).map(x->{review.setBook(x); return review;}).map(reviewRepo::save);
    }
}
