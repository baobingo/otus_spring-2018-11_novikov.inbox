package ru.otus.spring04.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleShellCommandTest {

    ShellCommand shellCommand = new SimpleShellCommand();

    @Test
    void justPrintOut() {
        assertEquals(shellCommand.justPrintOut("Test"), "Test");
    }
}