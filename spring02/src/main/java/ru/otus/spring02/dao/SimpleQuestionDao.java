package ru.otus.spring02.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import ru.otus.spring02.data.CSVLoader;
import ru.otus.spring02.data.SimpleCSVLoader;
import ru.otus.spring02.entity.Question;

import java.util.List;
import java.util.ListIterator;


@Repository
@PropertySource({"classpath:/application.properties"})
public class SimpleQuestionDao implements QuestionDao {

    private List<Question> questions;
    private Question current;
    private ListIterator<Question> listIterator;

    public SimpleQuestionDao(CSVLoader loader, @Value("${file.name}") String name, @Value("${locale.set}") String localSet) {
        questions = loader.load(SimpleCSVLoader.class.getResource("/" + name + "_" + localSet + ".csv").getPath());
        listIterator = questions.listIterator();
    }

    @Override
    public Question next() {
        current = listIterator.next();
        return current;
    }

    @Override
    public Question current() {
        return current;
    }

    @Override
    public int getCount() {
        return questions.size();
    }
}
