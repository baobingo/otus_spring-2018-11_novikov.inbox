package ru.otus.spring04.service;

public interface QuizService {
    String getQuestion();
    void checkAnswer(String answer);
    boolean loseWin();
    void startQuiz(String name, String surname);
    int questionCount();
    int previousIndex();
    int getScore();
}
