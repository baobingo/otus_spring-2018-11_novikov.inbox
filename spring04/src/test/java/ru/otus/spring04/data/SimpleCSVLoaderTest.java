package ru.otus.spring04.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.otus.spring04.configs.YamlProps;
import ru.otus.spring04.entity.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableConfigurationProperties(YamlProps.class)
@ContextConfiguration(classes = {SimpleCSVLoader.class})
@TestPropertySource(locations= "classpath:application.yml")
class SimpleCSVLoaderTest {

    @Autowired
    YamlProps props;

    @Autowired
    CSVLoader csvLoader;

    @Test
    void load() {
        List<Question> questions = csvLoader.load(SimpleCSVLoader.class.getResource("/" + props.getFilename() + "_" + props.getLocaleset() + ".csv").getPath());
        assertEquals(questions.size(), 5);
        assertEquals(questions.get(1).getQuestion(), "Кто президент Российской Федерации?");
    }
}