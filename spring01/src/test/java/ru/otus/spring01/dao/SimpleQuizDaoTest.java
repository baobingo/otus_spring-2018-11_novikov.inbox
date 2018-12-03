package ru.otus.spring01.dao;

import org.junit.jupiter.api.Test;
import ru.otus.spring01.entity.Quiz;

import static org.junit.jupiter.api.Assertions.*;

class SimpleQuizDaoTest {

    QuizDao quizDao = new SimpleQuizDao();

    @Test
    void getByNameSurname() {
        Quiz quiz = quizDao.getByNameSurname("Andrew" , "Novikov");
        assertEquals(quiz, quizDao.getByNameSurname("Vasya", "Pupkin"));
    }

    @Test
    void increaseScore() {
        Quiz quiz = quizDao.getByNameSurname("Andrew" , "Novikov");
        quizDao.increaseScore();
        assertEquals(quizDao.getScore(), 1);
        quizDao.increaseScore();
        quizDao.increaseScore();
        assertEquals(quizDao.getScore(), 3);
    }

    @Test
    void getScore() {
        Quiz quiz = quizDao.getByNameSurname("Andrew" , "Novikov");
        assertEquals(quizDao.getScore(), 0);
    }
}