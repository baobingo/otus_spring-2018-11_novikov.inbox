package ru.otus.spring03.data;

import ru.otus.spring03.entity.Question;

import java.util.List;

public interface CSVLoader {
    List<Question> load(String path);
}
