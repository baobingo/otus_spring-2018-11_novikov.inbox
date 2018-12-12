package ru.otus.spring02.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(value = {SpringExtension.class})
@TestPropertySource({"classpath:/application.properties"})
class SimpleMessageResourcesTest {

    MessageResource ms;

    public SimpleMessageResourcesTest(@Value("${bundle.base}") String name, @Value("${locale.set}") String locale) {
        this.ms =  new SimpleMessageResources(name, locale);
    }

    @Test
    void getI18nString() {
        assertEquals("А фамилия?", ms.getI18nString("surname"));
        assertEquals("выиграл", ms.getI18nString("win"));
    }

    @Configuration
    static class Config {

        @Bean
        public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
            return new PropertySourcesPlaceholderConfigurer();
        }

    }
}