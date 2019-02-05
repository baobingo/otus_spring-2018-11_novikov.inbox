package ru.otus.spring09.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring09.domain.Book;
import ru.otus.spring09.repository.BookRepository;
import ru.otus.spring09.service.SequenceService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class SimpleBookController implements BookController {

    private BookRepository bookRepository;
    private SequenceService sequenceService;

    public SimpleBookController(BookRepository bookRepository, SequenceService sequenceService) {
        this.bookRepository = bookRepository;
        this.sequenceService = sequenceService;
    }

    @Override
    @GetMapping("/")
    public void index(HttpServletResponse response) throws IOException{
        response.sendRedirect("/books");
    }

    @Override
    @GetMapping("/books")
    public String main(Model model) {
        List<Book> bookList = bookRepository.findAll();
        model.addAttribute("books", bookList);
        return "books";
    }

    @Override
    @GetMapping("/books/delete")
    public void deleteBook(HttpServletResponse response, @RequestParam("id") int bookId) throws IOException {
        bookRepository.deleteById((long)bookId);
        response.sendRedirect("/books");
    }

    @Override
    @GetMapping("/books/add")
    public String addBook(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "addbook";
    }

    @Override
    @PostMapping("/books/add")
    public void actionAdd(HttpServletResponse response, @ModelAttribute(value = "book") Book book, BindingResult bindingResult) throws IOException{
        if (bindingResult.hasErrors()) {
            response.sendRedirect("/books");
        }
        book.setId(sequenceService.getNextSequence());
        bookRepository.insert(book);
        response.sendRedirect("/books");
    }

    @Override
    @GetMapping("/books/edit")
    public String editBook(@RequestParam int id, Model model) throws IOException{
        Optional<Book> book = bookRepository.findById((long)id);
        book.orElseThrow(()->new IOException());
        model.addAttribute("book", book.get());
        return "editbook";
    }

    @Override
    @PostMapping("/books/edit")
    public void editBook(HttpServletResponse response, @ModelAttribute(value = "book") Book book, BindingResult bindingResult) throws IOException{
        if (bindingResult.hasErrors()) {
            response.sendRedirect("/books");
        }
        bookRepository.findById((long)book.getId()).ifPresent(x->{
            x.setName(book.getName());
            x.getAuthor().setName(book.getAuthor().getName());
            x.getGenre().setName(book.getGenre().getName());
            bookRepository.save(x);
        });
        response.sendRedirect("/books");
    }

    @Override
    @GetMapping("/books/review")
    public String review(@RequestParam int id, Model model) throws IOException {
        Optional<Book> book = bookRepository.findById((long)id);
        book.orElseThrow(()->new IOException());
        model.addAttribute("reviews", book.get().getReviews());
        return "review";
    }

    @ExceptionHandler({IOException.class, MissingServletRequestParameterException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleError() {
        return "404";
    }
}
