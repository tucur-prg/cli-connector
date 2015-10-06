package com.ns.monitor.command.rabbitmq;

import com.rabbitmq.client.MessageProperties;

public class Edset extends Connector implements Command {
    public void execute(String[] args) throws Exception {
        if (args.length < 1) {
            throw new IllegalArgumentException("invalid arguments");
        }

        this.client.set(this.client.getQueueName(), "", MessageProperties.PERSISTENT_TEXT_PLAIN, String.join(" ", args));
    }
}
