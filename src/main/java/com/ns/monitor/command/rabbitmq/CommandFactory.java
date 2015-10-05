package com.ns.monitor.command.rabbitmq;

import static java.lang.System.out;

public class CommandFactory {
    public static void help() {
        out.println("");
        out.println("RabbitMQ command:");
        out.println("");
        out.println("open <host> [user]");
        out.println("close");
        out.println("use <queueName>");
        out.println("create <queueName> <durable:on/off> <exclusive:on/off> <autoDelete:on/off>");
        out.println("set <message>");
        out.println("dset <message>");
        out.println("get");
    }

    public static Command doCommand(String cmd) {
        switch (cmd) {
            case "open":
                return new Open();
            case "close":
                return new Close();
            case "use":
                return new Use();
            case "create":
                return new Create();
            case "set":
                return new Set();
            case "dset":
                return new Dset();
            case "get":
                return new Get();
            default:
                throw new IllegalArgumentException(cmd + ": command not found");
        }
    }
}
