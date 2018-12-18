package ru.otus.spring02.data;

import ru.otus.spring02.entity.Question;

import java.util.List;

public interface CSVLoader {
    List<Question> load(String path);
}
