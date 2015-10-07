package com.ns.monitor.command.rabbitmq;

public class CreateExchange extends Connector implements Command {
    public void execute(String[] args) throws Exception {
        if (args.length < 3) {
            throw new IllegalArgumentException("invalid arguments");
        }

        this.client.createExchange(args[0], args[1], "on".equals(args[2]));
    }
}
