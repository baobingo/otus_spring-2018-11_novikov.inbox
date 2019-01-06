package ru.otus.spring05.ui;

import ru.otus.spring05.domain.Book;

import java.util.List;
import java.util.stream.Collectors;

public class SimpleHelper {
    public static String booksToText(List<Book> bookList){
        return bookList.stream().map(x->x.getName() + " ID: " + x.getId() + " Author: " + x.getAuthor().getName() + " Genre: " + x.getGenre().getName()+"\n").collect(Collectors.joining());
    }
}
