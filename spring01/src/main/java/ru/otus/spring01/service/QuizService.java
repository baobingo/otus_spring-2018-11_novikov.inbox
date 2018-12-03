package ru.otus.spring01.service;

public interface QuizService {
    public String getQuestion();
    public void checkAnswer(String answer);
    public boolean loseWin();
    public void startQuiz(String name, String surname);
}
