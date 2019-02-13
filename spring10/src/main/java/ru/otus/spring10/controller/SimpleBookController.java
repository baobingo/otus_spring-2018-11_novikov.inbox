package ru.otus.spring10.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring10.domain.Book;
import ru.otus.spring10.domain.Review;
import ru.otus.spring10.repository.BookRepository;
import ru.otus.spring10.service.SequenceService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class SimpleBookController {

    private BookRepository bookRepository;
    private SequenceService sequenceService;

    public SimpleBookController(BookRepository bookRepository, SequenceService sequenceService) {
        this.bookRepository = bookRepository;
        this.sequenceService = sequenceService;
    }

    @GetMapping("/api/books")
    public List<Book> booksList(){
        return bookRepository.findAll();
    }

    @GetMapping("/api/books/{id}/reviews")
    public List<Review> reviewsList(@PathVariable("id") String id) throws IOException{
        Optional<Book> optionalBook =  bookRepository.findById(Long.parseLong(id));
        optionalBook.orElseThrow(()->new IOException());
        return optionalBook.get().getReviews();
    }

    @GetMapping("/api/books/{id}")
    public Book getBookById(@PathVariable("id") String id) throws IOException{
        Optional<Book> optionalBook =  bookRepository.findById(Long.parseLong(id));
        optionalBook.orElseThrow(()->new IOException());
        return optionalBook.get();
    }

    @PutMapping("/api/books")
    public void updateBookById(@ModelAttribute(value = "book") Book book, BindingResult bindingResult) throws IOException{
        if (bindingResult.hasErrors()) {
            throw new IOException();
        }
        bookRepository.findById((long)book.getId()).ifPresent(x->{
            x.setName(book.getName());
            x.getAuthor().setName(book.getAuthor().getName());
            x.getGenre().setName(book.getGenre().getName());
            bookRepository.save(x);
        });
    }

    @PostMapping("/api/books")
    public void addBook(@ModelAttribute(value = "book") Book book, BindingResult bindingResult) throws IOException{
        if (bindingResult.hasErrors()) {
            throw new IOException();
        }
        book.setId(sequenceService.getNextSequence());
        bookRepository.insert(book);
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBookById(@PathVariable("id") int id) throws IOException{
        Optional<Book> optionalBook =  bookRepository.findById((long)id);
        optionalBook.orElseThrow(()->new IOException());
        bookRepository.delete(optionalBook.get());
    }

    @ExceptionHandler({IOException.class, MissingServletRequestParameterException.class})
    public String handleError() {
        return "Error";
    }
}
