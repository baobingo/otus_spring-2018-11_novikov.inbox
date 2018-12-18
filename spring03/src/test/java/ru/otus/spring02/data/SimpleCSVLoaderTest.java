package ru.otus.spring02.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.otus.spring02.configs.YamlProps;
import ru.otus.spring02.entity.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableConfigurationProperties(YamlProps.class)
class SimpleCSVLoaderTest {

    @Autowired
    YamlProps props;

    @Test
    void load() {
        CSVLoader csvLoader = new SimpleCSVLoader();
        List<Question> questions = csvLoader.load(SimpleCSVLoader.class.getResource("/" + props.getFilename() + "_" + props.getLocaleset() + ".csv").getPath());
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