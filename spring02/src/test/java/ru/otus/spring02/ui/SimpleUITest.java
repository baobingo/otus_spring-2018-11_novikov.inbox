package ru.otus.spring02.ui;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class SimpleUITest {

    Console console = new SimpleUI();

    @Test
    void read() throws IOException {
        String data = "Hello world!";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        assertEquals(console.read(), "Hello world!");
    }

    @Test
    void write() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
        console.write("Hello world!");
        assertEquals(baos.toString(), "Hello world!\r\n");
    }
}