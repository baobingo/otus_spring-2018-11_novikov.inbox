package ru.otus.spring01.dao;

import ru.otus.spring01.entity.Quiz;

public class SimpleQuizDao implements QuizDao {

    private Quiz current;

    @Override
    public Quiz getByNameSurname(String name, String surname) {
        if(current == null) {
            current = new Quiz(name, surname);
        }
        return current;
    }

    @Override
    public void increaseScore() {
        current.increaseScore();
    }

    @Override
    public int getScore() {
        return current.getScore();
    }
}
