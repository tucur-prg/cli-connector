package com.ns.monitor.command.couchbase;

public class Open extends Connector implements Command {
    public void execute(String[] args) throws Exception {
        if (args.length < 1) {
            throw new IllegalArgumentException("invalid arguments");
        }

        this.client.open(args[0]);
    }
}
