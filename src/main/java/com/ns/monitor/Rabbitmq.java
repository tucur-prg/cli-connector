package com.ns.monitor;

import java.io.IOException;

import jline.console.ConsoleReader;

import com.ns.connector.RabbitmqClient;

import com.ns.monitor.command.rabbitmq.*;

public class Rabbitmq extends Monitor {
    private RabbitmqClient client = new RabbitmqClient();

    Rabbitmq(ConsoleReader input) {
        super(input);
    }

    public String read() throws IOException {
        String queueName = client.getQueueName();
        if (queueName != null) {
            return c.readLine(String.format("rabbitmq:%s> ", queueName));
        }

        return c.readLine("rabbitmq> ");
    }

    public void shutdown() throws Exception {
        client.close();
    }

    @Override
    public void help() {
        super.help();
        CommandFactory.help();
    }

    protected void doCommand(String cmd, String... args) throws Exception {
        Command command = CommandFactory.doCommand(cmd);
        command.setClient(client).setConsole(c).execute(args);
    }
}
