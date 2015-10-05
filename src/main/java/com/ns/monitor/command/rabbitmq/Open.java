package com.ns.monitor.command.rabbitmq;

public class Open extends Connector implements Command {
    public void execute(String[] args) throws Exception {
        if (args.length < 1) {
            throw new IllegalArgumentException("invalid arguments");
        }

        if (args.length == 2) {
            String password = console.readLine("Password: ", new Character('*'));
            this.client.settings(args[1], password);
        }

        this.client.open(args[0]);
    }
}
