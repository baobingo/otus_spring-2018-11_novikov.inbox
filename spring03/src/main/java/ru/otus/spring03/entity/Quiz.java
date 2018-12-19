package ru.otus.spring03.entity;

public class Quiz {
    private String name;
    private String surname;
    private int score;

    public Quiz(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        this.score++;
    }
}
