package ru.otus.spring01.ui;

import java.util.Scanner;

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
