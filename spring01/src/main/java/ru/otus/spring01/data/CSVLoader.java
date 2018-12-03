package ru.otus.spring01.data;

import ru.otus.spring01.entity.Question;

import java.util.List;

public interface CSVLoader {
    public List<Question> load();
}
