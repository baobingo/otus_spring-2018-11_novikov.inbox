package ru.otus.spring01.dao;

import ru.otus.spring01.entity.Question;

public interface QuestionDao {
    Question next();
    Question current();
    int getCount();
}
