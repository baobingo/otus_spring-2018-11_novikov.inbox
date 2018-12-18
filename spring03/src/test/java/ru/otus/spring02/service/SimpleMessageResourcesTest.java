package ru.otus.spring02.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.otus.spring02.configs.YamlProps;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@EnableConfigurationProperties(YamlProps.class)
class SimpleMessageResourcesTest {

    MessageResource ms;

    @Autowired
    YamlProps props;

    @Test
    void getI18nString() {
        this.ms =  new SimpleMessageResources(props);
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