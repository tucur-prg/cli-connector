package com.ns.monitor;

import java.io.IOException;

import jline.console.ConsoleReader;

public class Base extends Monitor {
    Base(ConsoleReader input) {
        super(input);
    }

    public String read() throws IOException {
        return c.readLine("connector> ");
    }

    public void shutdown() throws Exception {
    }

    protected void doCommand(String cmd, String... args) throws Exception {
        throw new IllegalArgumentException(cmd + ": command not found");
    }
}
