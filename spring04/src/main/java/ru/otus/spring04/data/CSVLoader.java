package ru.otus.spring04.data;

import ru.otus.spring04.entity.Question;

import java.util.List;

public interface CSVLoader {
    List<Question> load(String path);
}
