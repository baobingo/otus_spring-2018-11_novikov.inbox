package ru.otus.spring04.dao;

import ru.otus.spring04.entity.Question;

public interface QuestionDao {
    Question next();
    Question current();
    int getCount();
    int previousIndex();
}
