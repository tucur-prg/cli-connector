package com.ns.monitor.command.rabbitmq;

public class Create extends Connector implements Command {
    public void execute(String[] args) throws Exception {
        if (args.length < 4) {
            throw new IllegalArgumentException("invalid arguments");
        }

        this.client.createQueue(args[0], "on".equals(args[1]), "on".equals(args[2]), "on".equals(args[3]));
    }
}
