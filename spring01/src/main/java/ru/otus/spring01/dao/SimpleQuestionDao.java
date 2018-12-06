package ru.otus.spring01.dao;

import ru.otus.spring01.data.CSVLoader;
import ru.otus.spring01.data.SimpleCSVLoader;
import ru.otus.spring01.entity.Question;

import java.util.List;
import java.util.ListIterator;

public class SimpleQuestionDao implements QuestionDao {

    private List<Question> questions;
    private Question current;
    private ListIterator<Question> listIterator;

    public SimpleQuestionDao(CSVLoader loader) {
        questions = loader.load(SimpleCSVLoader.class.getResource("/questions.csv").getPath());
        listIterator = questions.listIterator();
    }

    @Override
    public Question next() {
        current = listIterator.next();
        return current;
    }

    @Override
    public Question current() {
        return current;
    }

    @Override
    public int getCount() {
        return questions.size();
    }
}
