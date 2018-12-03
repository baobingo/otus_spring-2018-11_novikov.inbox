package ru.otus.spring01.dao;

import org.junit.jupiter.api.Test;
import ru.otus.spring01.data.SimpleCSVLoader;

import static org.junit.jupiter.api.Assertions.*;

class SimpleQuestionDaoTest {

    QuestionDao questionDao = new SimpleQuestionDao(new SimpleCSVLoader());

    @Test
    void next() {
        questionDao.next();
        assertEquals(questionDao.current().getQuestion(), "What year Pushkin was born?");
    }

    @Test
    void current() {
        questionDao.next();
        questionDao.next();
        assertEquals(questionDao.current().getQuestion(), "Who is the president of the Russian Federation?");
    }

    @Test
    void getCount() {
        assertEquals(questionDao.getCount(), 5);
    }
}