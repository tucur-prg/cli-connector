package com.ns.monitor;

import java.io.IOException;

import java.lang.Character;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jline.console.ConsoleReader;

import static java.lang.System.out;

public abstract class Monitor {
    private static Logger logger = LoggerFactory.getLogger(Monitor.class);
    protected ConsoleReader c;

    Monitor(ConsoleReader input) {
        this.c = input;
    }

    public abstract String read() throws IOException;
    public abstract void shutdown() throws Exception;

    public void execute(String cmd, String... args) throws Exception {
        if (!"".equals(cmd)) {
            try {
                doCommand(cmd, args);
            } catch (Exception e) {
                logger.error("exception", e);
                out.println("ERROR: " + e.getMessage());
            }
        }
    }

    public void help() {
        out.println("help         : Display this help.");
        out.println("list         : Supported Monitor list.");
        out.println("ch <monitor> : Change monitor.");
        out.println("exit         : Exit monitor. Same as quit.");
        out.println("quit         : Quit monitor.");
    }

    public void support() {
        out.println("couchbase");
        out.println("rabbitmq");
    }

    protected abstract void doCommand(String cmd, String... args) throws Exception;
}
