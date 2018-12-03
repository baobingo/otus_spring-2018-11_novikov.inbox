package ru.otus.spring01;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring01.service.QuizService;
import ru.otus.spring01.ui.Console;
import ru.otus.spring01.ui.SimpleUI;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        String name;
        String surname;

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");
        QuizService service = context.getBean(QuizService.class);
        Console console = context.getBean(SimpleUI.class);

        console.write("Let's play a game with you \nWhat's you name?");
        name = console.read();
        console.write("What's you family name?");
        surname = console.read();

        service.startQuiz(name, surname);

        IntStream.range(0,5).forEach(i ->{
            console.write(service.getQuestion());
            service.checkAnswer(console.read());
        });

        console.write("Your " + (service.loseWin()?"win":"lose") + ".");
    }
}
