package ru.otus.spring02.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.otus.spring02.configs.YamlProps;
import ru.otus.spring02.dao.SimpleQuestionDao;
import ru.otus.spring02.dao.SimpleQuizDao;
import ru.otus.spring02.data.SimpleCSVLoader;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@EnableConfigurationProperties(YamlProps.class)
@TestPropertySource(locations= "classpath:application.yml")
@ContextConfiguration(classes = {SimpleQuizService.class, SimpleQuestionDao.class, SimpleCSVLoader.class, SimpleQuizDao.class, SimpleMessageResources.class})
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