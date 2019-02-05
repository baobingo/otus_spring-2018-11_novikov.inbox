package ru.otus.spring09.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.otus.spring09.domain.Book;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface BookController {
    void index(HttpServletResponse response) throws IOException;
    String main(Model model);
    void deleteBook(HttpServletResponse response, int bookId) throws IOException;
    void actionAdd(HttpServletResponse response, Book book, BindingResult bindingResult) throws IOException;
    String addBook(Model model);
    String editBook(int id, Model model) throws IOException;
    void editBook(HttpServletResponse response, Book book, BindingResult bindingResult) throws IOException;
    String review(int id, Model model) throws IOException;;
}
