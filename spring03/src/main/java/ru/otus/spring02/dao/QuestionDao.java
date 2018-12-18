package ru.otus.spring02.dao;

import ru.otus.spring02.entity.Question;

public interface QuestionDao {
    Question next();
    Question current();
    int getCount();
}
