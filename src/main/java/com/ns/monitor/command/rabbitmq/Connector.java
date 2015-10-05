package com.ns.monitor.command.rabbitmq;

import com.ns.connector.RabbitmqClient;

import jline.console.ConsoleReader;

public abstract class Connector implements Command {
    protected RabbitmqClient client;
    protected ConsoleReader console;

    public Command setClient(RabbitmqClient client) {
        this.client = client;
        return this;
    }

    public Command setConsole(ConsoleReader console) {
        this.console = console;
        return this;
    }
}
