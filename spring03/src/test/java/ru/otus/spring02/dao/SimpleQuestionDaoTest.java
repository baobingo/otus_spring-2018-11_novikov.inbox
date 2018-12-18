package ru.otus.spring02.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.otus.spring02.configs.YamlProps;
import ru.otus.spring02.data.SimpleCSVLoader;
import ru.otus.spring02.service.SimpleMessageResources;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@EnableConfigurationProperties(YamlProps.class)
@ContextConfiguration(classes = {SimpleQuestionDao.class, SimpleCSVLoader.class, SimpleQuizDao.class, SimpleMessageResources.class})
@TestPropertySource(locations= "classpath:application.yml")
class SimpleQuestionDaoTest {

    @Autowired
    QuestionDao questionDao;

    @Autowired
    YamlProps props;

    @Test
    void next() {
        questionDao.next();
        assertEquals(questionDao.current().getQuestion(), "Год рождения Александра Сергеевича Пушкина?");
    }

    @Test
    void current() {
        this.questionDao = new SimpleQuestionDao(new SimpleCSVLoader(), props);
        questionDao.next();
        questionDao.next();
        assertEquals(questionDao.current().getQuestion(), "Кто президент Российской Федерации?");
    }

    @Test
    void getCount() {
        this.questionDao = new SimpleQuestionDao(new SimpleCSVLoader(), props);
        assertEquals(questionDao.getCount(), 5);
    }

}