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
        out.println("create_queue <queueName> <durable:on/off> <exclusive:on/off> <autoDelete:on/off>");
        out.println("create_exchange <exchangeName> <type> <durable:on/off>");
        out.println("bind_queue <queueName> <exchangeName> <routingKey>");
        out.println("unbind_queue <queueName> <exchangeName> <routingKey>");
        out.println("delete_queue <queueName>");
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
            case "create_queue":
                return new CreateQueue();
            case "create_exchange":
                return new CreateExchange();
            case "bind_queue":
                return new BindQueue();
            case "unbind_queue":
                return new UnbindQueue();
            case "delete_queue":
                return new DeleteQueue();
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
