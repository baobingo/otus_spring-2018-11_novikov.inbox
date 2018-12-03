package ru.otus.spring01.dao;

import ru.otus.spring01.entity.Question;

public interface QuestionDao {
    public Question next();
    public Question current();
    public int getCount();
}
