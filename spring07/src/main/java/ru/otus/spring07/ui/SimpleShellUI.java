package ru.otus.spring07.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.standard.commands.Help;
import ru.otus.spring07.domain.Author;
import ru.otus.spring07.domain.Book;
import ru.otus.spring07.domain.Genre;
import ru.otus.spring07.domain.Review;
import ru.otus.spring07.service.LibraryService;

@ShellComponent
public class SimpleShellUI implements Help.Command {

    @Autowired
    private LibraryService libraryService;

    @ShellMethod("Help info")
    public String help(){
        return "bookcount - Books count\n" +
                "authorcount - Authors count\n" +
                "genrecount - Genres count\n" +
                "allbook - Show all book\n" +
                "authorsbook [author] -  Get book by Author\n" +
                "genresbook [genre] - Get book by Genre\n" +
                "addbook [title] [author] [genre] - Add book\n" +
                "addauthor [name] - Add author\n" +
                "addgenre [name] -  Add genre\n" +
                "deletebook [id] - Delete book by id\n" +
                "deleteauthor [id] - Delete author by id\n" +
                "deletegenre [id] - Delete genre by id\n" +
                "addreview [author] [review] [bookId] - Add review\n" +
                "booksreviews [bookid] - Books reviews\n";
    }

    @ShellMethod(value = "Books count", key = "bookcount")
    public long bookCount(){
        return libraryService.bookCount();
    }

    @ShellMethod(value = "Authors count", key = "authorcount")
    public long authorCount(){
        return libraryService.authorCount();
    }

    @ShellMethod(value = "Genres count", key = "genrecount")
    public long genreCount(){
        return libraryService.genreCount();
    }

    @ShellMethod(value = "All Book", key = "allbook")
    public String allBook(){
        return SimpleHelper.booksToText(libraryService.allBook());
    }

    @ShellMethod(value = "Get book by authors", key = "authorsbook")
    public String authorsBook(@ShellOption String author){
        return SimpleHelper.booksToText(libraryService.authorsBooks(author));
    }

    @ShellMethod(value = "Get book by genre", key = "genresbook")
    public String genresBook(@ShellOption String genre){
        return SimpleHelper.booksToText(libraryService.genresBooks(genre));
    }

    @ShellMethod(value = "Add book", key = "addbook")
    public void addbook(@ShellOption String name, @ShellOption String author, @ShellOption String genre){
        libraryService.addBook(new Book(name, new Author(author), new Genre(genre)));
    }

    @ShellMethod(value = "Add author", key = "addauthor")
    public void addAuthor(@ShellOption String author){
        libraryService.addAuthor(new Author(author));
    }

    @ShellMethod(value = "Add genre", key = "addgenre")
    public void addGenre(@ShellOption String genre){
        libraryService.addGenre(new Genre(genre));
    }

    @ShellMethod(value = "Delete book by id", key = "deletebook")
    public void deleteBook(@ShellOption long id){
        libraryService.deleteBookById(id);
    }

    @ShellMethod(value = "Delete author by id", key = "deleteauthor")
    public void deleteAuthor(@ShellOption long id){
        libraryService.deleteAuthorById(id);
    }

    @ShellMethod(value = "Delete genre by id", key = "deletegenre")
    public void deleteGenre(@ShellOption long id) {
        libraryService.deleteGenreById(id);
    }

    @ShellMethod(value = "Add review", key = "addreview")
    public void addReview(@ShellOption String author, @ShellOption String body, @ShellOption int bookId){
        libraryService.addReview(new Review(author, body), bookId);
    }

    @ShellMethod(value = "Add review", key = "booksreviews")
    public String booksReview(@ShellOption int bookId){
        return SimpleHelper.reviewToText(libraryService.booksReviews(bookId));
    }
}
