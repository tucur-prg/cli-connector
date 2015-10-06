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
        out.println("bind <queueName> <exchangeName>");
        out.println("set <message>");
        out.println("eset <message>");
        out.println("dset <message>");
        out.println("edset <message>");
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
            case "bind":
                return new Bind();
            case "set":
                return new Set();
            case "eset":
                return new Eset();
            case "dset":
                return new Dset();
            case "edset":
                return new Edset();
            case "get":
                return new Get();
            default:
                throw new IllegalArgumentException(cmd + ": command not found");
        }
    }
}
