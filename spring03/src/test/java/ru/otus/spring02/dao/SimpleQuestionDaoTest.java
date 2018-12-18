package ru.otus.spring02.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.otus.spring02.configs.YamlProps;
import ru.otus.spring02.data.SimpleCSVLoader;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@EnableConfigurationProperties(YamlProps.class)
class SimpleQuestionDaoTest {

    QuestionDao questionDao;

    @Autowired
    YamlProps props;

    @Test
    void next() {
        this.questionDao = new SimpleQuestionDao(new SimpleCSVLoader(), props);
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

    @Configuration
    static class Config {

        @Bean
        public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
            return new PropertySourcesPlaceholderConfigurer();
        }

    }
}