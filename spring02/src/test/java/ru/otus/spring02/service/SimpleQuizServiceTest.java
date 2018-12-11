package ru.otus.spring02.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring02.Main;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(value = {SpringExtension.class})
@ContextConfiguration(classes = Main.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SimpleQuizServiceTest {

    @Autowired
    QuizService service;

    @Test
    void simpleStartAllAnswersCorrect(){
        service.startQuiz("Andrew", "Novikov");
        assertEquals(service.loseWin(), false);
        service.getQuestion();
        service.checkAnswer("1799");
        service.getQuestion();
        service.checkAnswer("Путин");
        service.getQuestion();
        service.checkAnswer("Огурец");
        service.getQuestion();
        service.checkAnswer("Интерфейс");
        service.getQuestion();
        service.checkAnswer("Отус");
        assertEquals(service.loseWin(), true);
    }

    @Test
    void simpleStartNotAllAnswersCorrect(){
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