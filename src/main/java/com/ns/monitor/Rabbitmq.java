package com.ns.monitor;

import java.lang.IllegalArgumentException;

import java.io.IOException;

import com.ns.connector.RabbitmqClient;

import static java.lang.System.out;

public class Rabbitmq extends Monitor {
    private RabbitmqClient conn = new RabbitmqClient();

    String queueName;

    public String read() throws IOException {
        if (queueName != null) {
            return c.readLine(String.format("rabbitmq:%s> ", queueName));
        }
        return c.readLine("rabbitmq> ");
    }

    public void shutdown() throws Exception {
        conn.close();
    }

    @Override
    public void help() {
        super.help();

        out.println("");
        out.println("RabbitMQ command:");
        out.println("");
        out.println("open <host> [user]");
        out.println("use <queueName>");
        out.println("create <queueName> <durable:on/off> <exclusive:on/off> <autoDelete:on/off>");
        out.println("set <message>");
        out.println("dset <message>");
        out.println("get");
    }

    protected void doCommand(String cmd, String... args) throws Exception {
//        String subCommand;
        String[] arguments;

        switch (cmd) {
            case "open":
                if (args.length < 1) {
                    throw new IllegalArgumentException("invalid arguments");
                }

                if (args.length == 2) {
                    char[] password = c.readPassword("Password: ");
                    conn.settings(args[1], String.valueOf(password));
                }

                conn.open(args[0]);
                break;
            case "use":
                if (args.length < 1) {
                    throw new IllegalArgumentException("invalid arguments");
                }

                conn.queue(args[0]);
                queueName = args[0];
                break;
            case "create":
                if (args.length < 4) {
                    throw new IllegalArgumentException("invalid arguments");
                }

                conn.createQueue(args[0], "on".equals(args[1]), "on".equals(args[2]), "on".equals(args[3]));
                break;
            case "set":
                if (args.length < 1) {
                    throw new IllegalArgumentException("invalid arguments");
                }

                if (queueName == null) {
                    throw new IllegalArgumentException("set queueName");
                }

                conn.set(queueName, String.join(" ", args));
                break;
            case "dset":
                if (args.length < 1) {
                    throw new IllegalArgumentException("invalid arguments");
                }

                if (queueName == null) {
                    throw new IllegalArgumentException("set queueName");
                }

                conn.dset(queueName, String.join(" ", args));
                break;
            case "get":
                if (queueName == null) {
                    throw new IllegalArgumentException("set queueName");
                }

                conn.get(queueName);
                break;
            default:
                throw new IllegalArgumentException(cmd + ": command not found");
        }
    }
}
