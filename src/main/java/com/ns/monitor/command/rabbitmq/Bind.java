package com.ns.monitor.command.rabbitmq;

public class Bind extends Connector implements Command {
    public void execute(String[] args) throws Exception {
        if (args.length < 2) {
            throw new IllegalArgumentException("invalid arguments");
        }

        this.client.bind(args[0], args[1]);
    }
}
