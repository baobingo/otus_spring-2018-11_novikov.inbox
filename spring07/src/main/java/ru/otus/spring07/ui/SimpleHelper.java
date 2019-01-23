package ru.otus.spring07.ui;

import ru.otus.spring07.domain.Book;
import ru.otus.spring07.domain.Review;

import java.util.List;
import java.util.stream.Collectors;

public class SimpleHelper {
    public static String booksToText(List<Book> bookList){
        return bookList.stream().map(x->x.getName() + " ID: " + x.getId() + " Author: " + x.getAuthor().getName() + " Genre: " + x.getGenre().getName()+"\n").collect(Collectors.joining());
    }
    public static String reviewToText(List<Review> reviewList){
        return reviewList.stream().map(x->" ID: " + x.getId() + " Author: " + x.getAuthor() + " Review: " + x.getBody()+"\n").collect(Collectors.joining());
    }
}
