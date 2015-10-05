package com.ns.monitor.command.rabbitmq;

public class Get extends Connector implements Command {
    public void execute(String[] args) throws Exception {
        this.client.get();
    }
}
