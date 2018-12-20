package ru.otus.spring04.dao;

import org.springframework.stereotype.Repository;
import ru.otus.spring04.configs.YamlProps;
import ru.otus.spring04.data.CSVLoader;
import ru.otus.spring04.data.SimpleCSVLoader;
import ru.otus.spring04.entity.Question;

import java.util.List;
import java.util.ListIterator;


@Repository
public class SimpleQuestionDao implements QuestionDao {

    private List<Question> questions;
    private Question current;
    private ListIterator<Question> listIterator;

    public SimpleQuestionDao(CSVLoader loader, YamlProps props) {
        questions = loader.load(SimpleCSVLoader.class.getResource("/" + props.getFilename() + "_" + props.getLocaleset() + ".csv").getPath());
        listIterator = questions.listIterator();
    }

    @Override
    public Question next() {
        if(listIterator.hasNext()) {
            current = listIterator.next();
        }else{
            listIterator = questions.listIterator();
            current = listIterator.next();
        }
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

    @Override
    public int previousIndex() {
        return listIterator.previousIndex();
    }
}
