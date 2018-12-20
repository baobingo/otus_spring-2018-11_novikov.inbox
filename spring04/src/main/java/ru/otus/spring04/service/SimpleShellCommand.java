package ru.otus.spring04.service;

import org.springframework.stereotype.Component;

@Component
public class SimpleShellCommand implements ShellCommand {
    @Override
    public String justPrintOut(String message) {
        return message;
    }
}
