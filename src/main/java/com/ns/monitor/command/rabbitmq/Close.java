package com.ns.monitor.command.rabbitmq;

public class Close extends Connector implements Command {
    public void execute(String[] args) throws Exception {
        this.client.close();
    }
}
