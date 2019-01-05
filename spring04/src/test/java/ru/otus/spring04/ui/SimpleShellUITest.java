package ru.otus.spring04.ui;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SimpleShellUITest{

    @Autowired
    private SimpleShellUI simpleShellUI;

    @Test
    void start() {
        assertEquals(simpleShellUI.start("Андрей","Новиков"), "Год рождения Александра Сергеевича Пушкина?");
    }

    @Test
    void help() {
        assertTrue(simpleShellUI.help().length()>0);
    }

    @Test
    void answer() {
        assertEquals(simpleShellUI.start("Андрей","Новиков"), "Год рождения Александра Сергеевича Пушкина?");
        assertEquals(simpleShellUI.answer("1799"), "Кто президент Российской Федерации?");
    }
}