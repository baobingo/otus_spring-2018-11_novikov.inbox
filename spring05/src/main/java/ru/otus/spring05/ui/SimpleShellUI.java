package ru.otus.spring05.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.standard.commands.Help;
import ru.otus.spring05.service.LibraryService;

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
                "deletegenre [id] - Delete genre by id\n";
    }

    @ShellMethod("Books count")
    public int bookcount(){
        return libraryService.bookCount();
    }

    @ShellMethod("Authors count")
    public int authorcount(){
        return libraryService.authorCount();
    }

    @ShellMethod("Genres count")
    public int genrecount(){
        return libraryService.genreCount();
    }

    @ShellMethod("All Book")
    public String allbook(){
        return SimpleHelper.booksToText(libraryService.allBook());
    }

    @ShellMethod("Get book by authors")
    public String authorsbook(@ShellOption String author){
        return SimpleHelper.booksToText(libraryService.authorsBooks(author));
    }

    @ShellMethod("Get book by genre")
    public String genresbook(@ShellOption String genre){
        return SimpleHelper.booksToText(libraryService.genresBooks(genre));
    }

    @ShellMethod("Add book")
    public void addbook(@ShellOption String name, @ShellOption String author, @ShellOption String genre){
        libraryService.addBook(name, author, genre);
    }

    @ShellMethod("Add author")
    public void addauthor(@ShellOption String author){
        libraryService.addAuthor(author);
    }

    @ShellMethod("Add genre")
    public void addgenre(@ShellOption String genre){
        libraryService.addGenre(genre);
    }

    @ShellMethod("Delete book by id")
    public void deletebook(@ShellOption long id){
        libraryService.deleteBookById(id);
    }

    @ShellMethod("Delete author by id")
    public void deleteauthor(@ShellOption long id){
        libraryService.deleteAuthorById(id);
    }

    @ShellMethod("Delete genre by id")
    public void deletegenre(@ShellOption long id) {
        libraryService.deleteGenreById(id);
    }
}
