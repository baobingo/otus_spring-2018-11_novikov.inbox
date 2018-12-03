package ru.otus.spring01.service;

import ru.otus.spring01.dao.QuestionDao;
import ru.otus.spring01.dao.QuizDao;

public class SimpleQuizService implements QuizService {

    private QuestionDao questionDao;
    private QuizDao quizDao;

    public SimpleQuizService(QuestionDao questionDao, QuizDao quizDao) {
        this.questionDao = questionDao;
        this.quizDao = quizDao;
    }

    @Override
    public String getQuestion() {
        return questionDao.next().getQuestion();
    }

    @Override
    public void checkAnswer(String answer) {
        if(questionDao.current().getAnswer().equals(answer)){
            quizDao.increaseScore();
        }
    }

    @Override
    public boolean loseWin() {
        if(quizDao.getScore() == questionDao.getCount()){
            return true;
        }
        return false;
    }

    @Override
    public void startQuiz(String name, String surname) {
        quizDao.getByNameSurname(name, surname);
    }
}
