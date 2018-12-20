package ru.otus.spring04.dao;

import ru.otus.spring04.entity.Quiz;

public interface QuizDao {
    Quiz getByNameSurname(String name, String surname);
    void increaseScore();
    int getScore();
}
