package ru.otus.spring01.dao;

import ru.otus.spring01.entity.Quiz;

public interface QuizDao {
    Quiz getByNameSurname(String name, String surname);
    void increaseScore();
    int getScore();
}
