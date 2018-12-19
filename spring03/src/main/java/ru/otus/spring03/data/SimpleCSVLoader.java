package ru.otus.spring03.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import ru.otus.spring03.entity.Question;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class SimpleCSVLoader implements CSVLoader {

    @Override
    public List<Question> load(String path) {
        List<Question> questions = new ArrayList<>();
        try {
            Reader in = new InputStreamReader(new FileInputStream(URLDecoder.decode(path, "UTF-8")), "UTF-8");
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
            for (CSVRecord record : records) {
                if(record.size() == 2) {
                    questions.add(new Question(record.get(0), record.get(1)));
                }
            }
        }catch (IOException e){
            System.out.println("Question file not found, fix it dude.");
            System.exit(-1);
        }
        return questions;
    }

}
