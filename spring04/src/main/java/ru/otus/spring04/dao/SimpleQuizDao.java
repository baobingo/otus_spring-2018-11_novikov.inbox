package ru.otus.spring04.dao;

import org.springframework.stereotype.Repository;
import ru.otus.spring04.entity.Quiz;

@Repository
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
