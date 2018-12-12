package ru.otus.spring02.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring02.data.SimpleCSVLoader;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(value = {SpringExtension.class})
@TestPropertySource({"classpath:/application.properties"})
class SimpleQuestionDaoTest {

    QuestionDao questionDao;

    public SimpleQuestionDaoTest(@Value("${file.name}") String name, @Value("${locale.set}") String localSet) {
        this.questionDao = new SimpleQuestionDao(new SimpleCSVLoader(), name, localSet);
    }

    @Test
    void next() {
        questionDao.next();
        assertEquals(questionDao.current().getQuestion(), "Год рождения Александра Сергеевича Пушкина?");
    }

    @Test
    void current() {
        questionDao.next();
        questionDao.next();
        assertEquals(questionDao.current().getQuestion(), "Кто президент Российской Федерации?");
    }

    @Test
    void getCount() {
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