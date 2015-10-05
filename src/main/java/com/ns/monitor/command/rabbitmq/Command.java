package com.ns.monitor.command.rabbitmq;

import com.ns.connector.RabbitmqClient;

import jline.console.ConsoleReader;

public interface Command {
    public Command setClient(RabbitmqClient client);
    public Command setConsole(ConsoleReader console);
    public void execute(String[] args) throws Exception;
}
