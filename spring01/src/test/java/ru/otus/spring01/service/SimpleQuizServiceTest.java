package ru.otus.spring01.service;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class SimpleQuizServiceTest {

    @Test
    void simpleStartAllAnswersCorrect(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");
        QuizService service = context.getBean(QuizService.class);
        service.startQuiz("Andrew", "Novikov");
        assertEquals(service.loseWin(), false);
        service.getQuestion();
        service.checkAnswer("1799");
        service.getQuestion();
        service.checkAnswer("Putin");
        service.getQuestion();
        service.checkAnswer("Cucumber");
        service.getQuestion();
        service.checkAnswer("Interface");
        service.getQuestion();
        service.checkAnswer("Otus");
        assertEquals(service.loseWin(), true);
    }

    @Test
    void simpleStartNotAllAnswersCorrect(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");
        QuizService service = context.getBean(QuizService.class);
        service.startQuiz("Andrew", "Novikov");
        assertEquals(service.loseWin(), false);
        service.getQuestion();
        service.checkAnswer("1799");
        service.getQuestion();
        service.checkAnswer("Putin");
        service.getQuestion();
        service.checkAnswer("Cucumber");
        service.getQuestion();
        service.checkAnswer("Interface");
        service.getQuestion();
        service.checkAnswer("Geekbrain");
        assertEquals(service.loseWin(), false);
    }
}