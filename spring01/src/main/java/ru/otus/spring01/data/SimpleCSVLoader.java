package ru.otus.spring01.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import ru.otus.spring01.entity.Question;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class SimpleCSVLoader implements CSVLoader {
    @Override
    public List<Question> load(String path) {
        List<Question> questions = new ArrayList<>();
        try {
            Reader in = new FileReader(path);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
            for (CSVRecord record : records) {
                if(record.size() == 2) {
                    questions.add(new Question(record.get(0), record.get(1)));
                }
            }
        }catch (IOException e){
            System.exit(-1);
        }
        return questions;
    }

}
