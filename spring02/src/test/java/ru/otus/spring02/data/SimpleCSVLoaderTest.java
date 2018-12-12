package ru.otus.spring02.data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring02.entity.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(value = {SpringExtension.class})
@TestPropertySource({"classpath:/application.properties"})
class SimpleCSVLoaderTest {

    @Test
    void load(@Value("${file.name}") String name, @Value("${locale.set}") String localSet) {
        CSVLoader csvLoader = new SimpleCSVLoader();
        List<Question> questions = csvLoader.load(SimpleCSVLoader.class.getResource("/" + name + "_" + localSet + ".csv").getPath());
        assertEquals(questions.size(), 5);
        assertEquals(questions.get(1).getQuestion(), "Кто президент Российской Федерации?");
    }

    @Configuration
    static class Config {

        @Bean
        public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
            return new PropertySourcesPlaceholderConfigurer();
        }

    }
}