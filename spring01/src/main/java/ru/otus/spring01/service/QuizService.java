package ru.otus.spring01.service;

public interface QuizService {
    String getQuestion();
    void checkAnswer(String answer);
    boolean loseWin();
    void startQuiz(String name, String surname);
    int questionCount();
}
