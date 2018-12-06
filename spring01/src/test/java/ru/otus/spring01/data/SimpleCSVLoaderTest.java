package ru.otus.spring01.data;

import org.junit.jupiter.api.Test;
import ru.otus.spring01.entity.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleCSVLoaderTest {

    @Test
    void load() {
        CSVLoader csvLoader = new SimpleCSVLoader();
        List<Question> questions = csvLoader.load(SimpleCSVLoaderTest.class.getResource("/questions.csv").getPath());
        assertEquals(questions.size(), 5);
        assertEquals(questions.get(0).getQuestion(), "What year Pushkin was born?");
    }
}