package ru.otus.spring03.dao;

import ru.otus.spring03.entity.Question;

public interface QuestionDao {
    Question next();
    Question current();
    int getCount();
}
