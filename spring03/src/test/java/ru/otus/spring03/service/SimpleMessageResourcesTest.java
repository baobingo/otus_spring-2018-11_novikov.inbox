package ru.otus.spring03.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.otus.spring03.configs.YamlProps;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@EnableConfigurationProperties(YamlProps.class)
@ContextConfiguration(classes = {SimpleMessageResources.class})
@TestPropertySource(locations= "classpath:application.yml")

class SimpleMessageResourcesTest {

    @Autowired
    MessageResource ms;

    @Test
    void getI18nString() {
        assertEquals("А фамилия?", ms.getI18nString("surname"));
        assertEquals("выиграл", ms.getI18nString("win"));
    }
}