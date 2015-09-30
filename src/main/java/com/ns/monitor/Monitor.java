package com.ns.monitor;

import java.io.File;
import java.io.Console;
import java.io.IOException;

import java.lang.Character;

import jline.console.ConsoleReader;
import jline.console.history.FileHistory;

import static java.lang.System.out;

public abstract class Monitor {
    protected ConsoleReader c;
    protected Character mask = new Character('*');

    Monitor() {
        try {
            c = new ConsoleReader(System.in, System.err);
            if (c == null) {
                System.err.println("No console.");
                System.exit(0);
            }

            final FileHistory history = new FileHistory(new File(System.getProperty("user.home"), ".cli_history"));
            c.setHistory(history);

            Runtime.getRuntime().addShutdownHook(
                new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                history.flush();
                            } catch (java.io.IOException e) {
                                System.err.println("Failed to flush command history! " + e);
                            }
                        }
                    }
                )
            );
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
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
