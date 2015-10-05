package com.ns.monitor;

import jline.console.ConsoleReader;

public class MonitorFactory {
    public static Monitor getInstance(ConsoleReader input, String name) {
        switch (name) {
            case "couchbase":
                return new Couchbase(input);
            case "rabbitmq":
                return new Rabbitmq(input);
            case "base":
                return new Base(input);
            default:
                System.out.println("ERROR: " + name + ": unsupported monitor");
                return new Base(input);
        }
    }
}
