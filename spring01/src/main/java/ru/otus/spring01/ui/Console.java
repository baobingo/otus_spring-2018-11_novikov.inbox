package ru.otus.spring01.ui;

import java.io.InputStream;
import java.io.PrintStream;

public interface Console {
    public String read();
    public void write(String message);
}
