package com.ns.monitor;

import java.io.Console;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import static java.lang.System.out;

public abstract class Monitor {
    protected Console c = System.console();

    Monitor() {
        if (c == null) {
            System.err.println("No console.");
            System.exit(0);
        }
    }

    public String read() throws IOException {
        return c.readLine("> ");
    }

    public abstract void shutdown() throws Exception;

    public void execute(String cmd, String... args) throws Exception {
        if (!"".equals(cmd)) {
            try {
                doCommand(cmd, args);
            } catch (Exception e) {
                out.println("ERROR: " + e.getMessage());
            }
        }
    }

    public void help() {
        out.println("help             : Display this help.");
        out.println("list             : Supported Monitor list.");
        out.println("change <monitor> : Change monitor.");
        out.println("exit             : Exit monitor. Same as quit.");
        out.println("quit             : Quit monitor.");
    }

    public void support() {
        out.println("couchbase");
        out.println("rabbitmq");
    }

    protected abstract void doCommand(String cmd, String... args) throws Exception;
}
