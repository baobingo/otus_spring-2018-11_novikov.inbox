package ru.otus.spring03.dao;

import ru.otus.spring03.entity.Quiz;

public interface QuizDao {
    Quiz getByNameSurname(String name, String surname);
    void increaseScore();
    int getScore();
}
