package ru.otus.spring01.dao;

import ru.otus.spring01.entity.Quiz;

public interface QuizDao {
    public Quiz getByNameSurname(String name, String surname);
    public void increaseScore();
    public int getScore();
}
