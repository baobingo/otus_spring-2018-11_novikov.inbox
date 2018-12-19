package ru.otus.spring03.ui;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class SimpleUI implements Console{

    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public void write(String message) {
        System.out.println(message);
    }
}
