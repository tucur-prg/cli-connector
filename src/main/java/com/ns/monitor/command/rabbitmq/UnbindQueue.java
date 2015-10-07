package com.ns.monitor.command.rabbitmq;

public class UnbindQueue extends Connector implements Command {
    public void execute(String[] args) throws Exception {
        if (args.length < 2) {
            throw new IllegalArgumentException("invalid arguments");
        }

        this.client.unbindQueue(args[0], args[1], args.length == 2 ? "" : args[2]);
    }
}
