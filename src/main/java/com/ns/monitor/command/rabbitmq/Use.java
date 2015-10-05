package com.ns.monitor.command.rabbitmq;

public class Use extends Connector implements Command {
    public void execute(String[] args) throws Exception {
        if (args.length < 1) {
            throw new IllegalArgumentException("invalid arguments");
        }

        this.client.queue(args[0]);
    }
}
