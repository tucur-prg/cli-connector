package com.ns;

import java.io.Console;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Properties;

import com.ns.monitor.*;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args) throws Exception {
        Console c = System.console();
        if (c == null) {
            System.err.println("No console.");
            System.exit(0);
        }

        InputStream is = ClassLoader.getSystemResourceAsStream("main.properties");
        Properties configuration = new Properties();
        configuration.load(is);
        is.close();

        out.println("");
        out.println("CLI Connector version " + configuration.getProperty("version"));
        out.println("");
        out.println("Type 'help' for help");
        out.println("");

        Monitor monitor = changeMonitor("rabbitmq");

        while (true) {
            String line = monitor.read().trim();
            String[] array = line.split(" ");
            if (array.length == 0) {
                continue;
            }

            String command = array[0].toLowerCase();
            String[] arguments = new String[0];
            if (array.length > 1) {
                arguments = new String[array.length - 1];
                System.arraycopy(array, 1, arguments, 0, arguments.length);
            }

            if ("exit".equals(command) || "quit".equals(command)) {
                monitor.shutdown();
                out.println("Bye");
                break;
            } else if ("help".equals(command)) {
                monitor.help();
                continue;
            } else if ("list".equals(command)) {
                monitor.support();
                continue;
            } else if ("change".equals(command)) {
                if (arguments.length > 0) {
                    monitor = changeMonitor(arguments[0]);
                } else {
                    monitor = changeMonitor("base");
                }

                continue;
            }

            monitor.execute(command, arguments);
        }
    }

    public static Monitor changeMonitor(String type) {
        switch (type) {
            case "couchbase":
                return new Couchbase();
            case "rabbitmq":
                return new Rabbitmq();
            case "base":
                return new Base();
            default:
                out.println("ERROR: " + type + ": unsupported monitor");
                return new Base();
        }
    }
}
