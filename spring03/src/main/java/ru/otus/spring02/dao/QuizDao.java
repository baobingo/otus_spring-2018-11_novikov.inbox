package ru.otus.spring02.dao;

import ru.otus.spring02.entity.Quiz;

public interface QuizDao {
    Quiz getByNameSurname(String name, String surname);
    void increaseScore();
    int getScore();
}
