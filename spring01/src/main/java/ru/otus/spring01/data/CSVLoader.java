package ru.otus.spring01.data;

import ru.otus.spring01.entity.Question;

import java.net.URL;
import java.util.List;

public interface CSVLoader {
    List<Question> load(String path);
}
