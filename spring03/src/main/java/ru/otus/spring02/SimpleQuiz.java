package ru.otus.spring02;

import org.springframework.stereotype.Service;
import ru.otus.spring02.service.MessageResource;
import ru.otus.spring02.service.QuizService;
import ru.otus.spring02.ui.Console;

import java.util.stream.IntStream;

@Service
public class SimpleQuiz implements Quiz{

    private QuizService service;
    private Console console;
    private MessageResource ms;


    public SimpleQuiz(QuizService service, Console console, MessageResource ms) {
        this.service = service;
        this.console = console;
        this.ms = ms;
    }

    public void startQuiz(){
        String name;
        String surname;

        console.write(ms.getI18nString("name"));
        name = console.read();
        console.write(ms.getI18nString("surname"));
        surname = console.read();

        service.startQuiz(name, surname);

        IntStream.range(0, service.questionCount()).forEach(i ->{
            console.write(service.getQuestion());
            service.checkAnswer(console.read());
        });

        console.write(ms.getI18nString("you")+ " " + (service.loseWin()?ms.getI18nString("win"):ms.getI18nString("lose")) + ".");
    }
}
