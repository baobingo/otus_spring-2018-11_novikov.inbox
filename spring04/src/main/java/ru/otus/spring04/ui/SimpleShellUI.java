package ru.otus.spring04.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.standard.commands.Help;
import ru.otus.spring04.service.MessageResource;
import ru.otus.spring04.service.QuizService;


@ShellComponent
public class SimpleShellUI implements Help.Command {
    @Autowired
    private MessageResource ms;
    @Autowired
    private QuizService service;

    @ShellMethod("Start Quiz")
    public String start(@ShellOption String name, @ShellOption String surname) {
        service.startQuiz(name, surname);
        return service.getQuestion();
    }

    @ShellMethod("Help info")
    public String help(){
        return ms.getI18nString("help_info");
    }

    @ShellMethod("Answer")
    public String answer(@ShellOption String answer){
        service.checkAnswer(answer);
        if(service.previousIndex() == service.questionCount()-1){
            return ms.getI18nString("show_result");
        }else{
            return service.getQuestion();
        }
    }

    @ShellMethod("Show result")
    public String show_result(){
        if(service.previousIndex() < service.questionCount()-1){
            return ms.getI18nString("notice");
        }
        return ms.getI18nString("you")+ " " + (service.loseWin()?ms.getI18nString("win"):ms.getI18nString("lose")) + ".";
    }
}
