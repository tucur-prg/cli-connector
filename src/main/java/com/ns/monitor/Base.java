package com.ns.monitor;

import java.io.IOException;

public class Base extends Monitor {
    public String read() throws IOException {
        return c.readLine("connector> ");
    }

    public void shutdown() throws Exception {
    }

    protected void doCommand(String cmd, String... args) throws Exception {
        System.out.println("ERROR: " + cmd + ": command not found");
    }
}
