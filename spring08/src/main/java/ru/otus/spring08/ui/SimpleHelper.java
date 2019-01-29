package ru.otus.spring08.ui;

import java.util.List;
import java.util.stream.Collectors;

public class SimpleHelper {
    public static <T> String toOutput(List<T> bookList){
        return bookList.stream().map(x->x.toString()).collect(Collectors.joining());
    }
}
