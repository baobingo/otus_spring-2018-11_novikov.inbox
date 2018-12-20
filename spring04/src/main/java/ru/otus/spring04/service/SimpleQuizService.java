package ru.otus.spring04.service;

import org.springframework.stereotype.Service;
import ru.otus.spring04.dao.QuestionDao;
import ru.otus.spring04.dao.QuizDao;

@Service
public class SimpleQuizService implements QuizService {

    private QuestionDao questionDao;
    private QuizDao quizDao;
    private MessageResource ms;

    public SimpleQuizService(QuestionDao questionDao, QuizDao quizDao, MessageResource ms) {
        this.questionDao = questionDao;
        this.quizDao = quizDao;
        this.ms = ms;
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

    @Override
    public int questionCount() {
        return questionDao.getCount();
    }

    @Override
    public int previousIndex() {
        return questionDao.previousIndex();
    }

    @Override
    public int getScore() {
        return getScore();
    }
}
