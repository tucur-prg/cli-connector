package com.ns;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import com.ns.monitor.Monitor;
import com.ns.monitor.MonitorFactory;

import jline.console.ConsoleReader;
import jline.console.history.FileHistory;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args) throws Exception {
        InputStream is = ClassLoader.getSystemResourceAsStream("main.properties");
        Properties configuration = new Properties();
        configuration.load(is);
        is.close();

        ConsoleReader input = new ConsoleReader(System.in, System.err);
        if (input == null) {
            System.err.println("No console.");
            System.exit(0);
        }

        try {
            final FileHistory history = new FileHistory(new File(System.getProperty("user.home"), configuration.getProperty("history_path")));
            input.setHistory(history);

            Runtime.getRuntime().addShutdownHook(
                new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                history.flush();
                            } catch (IOException e) {
                                System.err.println("Failed to flush command history! " + e);
                            }
                        }
                    }
                )
            );
        } catch (IOException e) {
            System.err.println("ERROR: " + e);
            System.exit(0);
        }

        out.println("");
        out.println("CLI Connector version " + configuration.getProperty("version"));
        out.println("");
        out.println("Type 'help' for help");
        out.println("");

        Monitor monitor = MonitorFactory.getInstance(input, "base");

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
            } else if ("ch".equals(command)) {
                if (arguments.length > 0) {
                    monitor = MonitorFactory.getInstance(input, arguments[0]);
                } else {
                    monitor = MonitorFactory.getInstance(input, "base");
                }

                continue;
            }

            monitor.execute(command, arguments);
        }
    }
}
