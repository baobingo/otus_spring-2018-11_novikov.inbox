package ru.otus.spring06.service;

import org.springframework.stereotype.Service;
import ru.otus.spring06.repo.AuthorRepo;
import ru.otus.spring06.repo.BookRepo;
import ru.otus.spring06.repo.GenreRepo;
import ru.otus.spring06.repo.ReviewRepo;
import ru.otus.spring06.domain.Author;
import ru.otus.spring06.domain.Book;
import ru.otus.spring06.domain.Genre;
import ru.otus.spring06.domain.Review;

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
    public int bookCount() {
        return bookRepo.count();
    }

    @Override
    public int authorCount() {
        return authorRepo.count();
    }

    @Override
    public int genreCount() {
        return genreRepo.count();
    }

    @Override
    public List<Book> allBook() {
        return bookRepo.getAll();
    }

    @Override
    public List<Book> authorsBooks(String authorsName) {
        Author author = authorRepo.getByName(authorsName);
        return bookRepo.getByAuthor(author);
    }

    @Override
    public List<Book> genresBooks(String genresTitle) {
        Genre genre = genreRepo.getByName(genresTitle);
        return bookRepo.getByGenre(genre);
    }


    @Override
    public void addBook(Book book) {
        authorRepo.insertOrId(book.getAuthor());
        genreRepo.insertOrId(book.getGenre());
        bookRepo.insert(book);
    }

    @Override
    public void addAuthor(Author author) {
        String name = author.getName();
        Optional.ofNullable(authorRepo.getByName(name)).orElseGet(()-> {
            authorRepo.insert(new Author(name));
            return authorRepo.getByName(name);
        });
    }

    @Override
    public void addGenre(Genre genre) {
        String name = genre.getName();
        Optional.ofNullable(genreRepo.getByName(name)).orElseGet(()-> {
            genreRepo.insert(new Genre(name));
            return genreRepo.getByName(name);
        });
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
    public List<Review> booksReviews(int booksId) {
        return reviewRepo.getByBook(bookRepo.getByID(booksId));
    }

    @Override
    public void addReview(Review review, int bookId) {
        review.setBook(bookRepo.getByID(bookId));
        reviewRepo.insert(review);
    }
}
